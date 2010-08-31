/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.puzzlebazar.server.model;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.dyuproject.openid.ext.AxSchemaExtension;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.puzzlebazar.shared.ObjectNotFoundException;
import com.puzzlebazar.shared.TransactionFailedException;
import com.puzzlebazar.shared.model.InvalidEditException;
import com.puzzlebazar.shared.model.User;
import com.puzzlebazar.shared.model.UserImpl;
import com.puzzlebazar.shared.util.AvailableLocales;

/**
 * The class responsible of managing cache and datastore
 * storage of user-related objects. These objects are managed here:
 * <ul>
 * <li> {@link Session} </li>
 * <li> {@link UserImpl} </li>
 * <li> {@link EmailToUser} </li>
 * </ul>
 * 
 * @author Philippe Beaudoin
 */
public class UserDAO extends DAOBase {  
  
  private static boolean objectsRegistered;
  
  @Override
  protected boolean areObjectsRegistered() {
    return objectsRegistered;
  }
  
  @Override
  protected void registerObjects(ObjectifyFactory ofyFactory) {
    objectsRegistered = true;
    ofyFactory.register(Session.class);
    ofyFactory.register(UserImpl.class);
    ofyFactory.register(EmailToUser.class);
  }    
  
  private static final int MAX_RETRIES = 5; // Number of retries before giving up
  private static final Object ADMINISTRATOR_EMAIL = "philippe.beaudoin@gmail.com";
  private final Logger logger;
  private final AvailableLocales availableLocales;
  private final Provider<HttpSession> session;
  private final Provider<HttpServletRequest> request;
  private final Provider<HttpServletResponse> response;
  
  @Inject
  public UserDAO(
      final ObjectifyFactory objectifyFactory,
      final Logger logger,
      final AvailableLocales availableLocales,
      final Provider<HttpSession> session,
      final Provider<HttpServletRequest> request,
      final Provider<HttpServletResponse> response) {
    super(objectifyFactory);
    this.logger = logger;
    this.availableLocales = availableLocales;
    this.session = session;
    this.request = request;
    this.response = response;  
  }

  /**
   * Access the key of the user currently logged into the session, or {@code null} if
   * not logged in.
   * 
   * @return The key of the {@link User} currently logged-in, or {@code null} if no user is logged in. 
   */
  public Key<UserImpl> getSessionUserKey() {
    String sessionId = getSessionId();  
    return getOrCreateSession(sessionId).getCurrentUserKey();        
  }

  /**
   * Access the user currently logged into the session, or {@code null} if 
   * not logged in.
   * 
   * @return The {@link User} currently logged-in, or {@code null} if no user is logged in.
   */
  public User getSessionUser() {
    Key<UserImpl> userKey = getSessionUserKey();
    
    // Special case: A null user key means the user is known not to be logged-in
    //               this saves a trip to the datastore.
    if (userKey == null) {
      return null;
    }
    
    User user = getUser(userKey);
    if (user == null) {
      createInvalidSession(getSessionId());
    } else {
      ((UserImpl) user).setAuthenticated(true);
    }
    return user;
  }

  /**
   * Sets the user currently logged into the session. This should
   * be called by the OpenId servlet when the OpenId provider 
   * authenticates the user.
   * 
   * @param openIdUser The {@link OpenIdUser} currently logged into the session.
   * @throws TransactionFailedException If the user cannot be created.
   */
  public void setSessionUser(OpenIdUser openIdUser) throws TransactionFailedException {
  
    Map<String,String> axSchema = AxSchemaExtension.get(openIdUser);
    String email = axSchema.get("email");
    if (email == null) {
      logger.warning("Setting a user with a null email. Call logout instead?");
      return;
    }
    String locale =  availableLocales.getBestLocale(axSchema.get("language")).getLocale();
  
    User user = getUserByEmailOrCreate(email, locale);
    if (user != null) {
      String sessionId = getSessionId();
      ofy().put(new Session(sessionId, user.createKey()));
    }
  }

  /**
   * Access the user given a user key.
   * 
   * @param id The id for the user to get.
   * @return The {@link User} obtained from the datastore, or {@code null} if none found.
   */
  public User getUser(
      final long id) {
    return getUser(new Key<UserImpl>(UserImpl.class, id));
  }

  /**
   * Access the user given a user key.
   * 
   * @param key The key for the user to get.
   * @return The {@link User} obtained from the datastore, or {@code null} if none found.
   */
  public User getUser(Key<UserImpl> key) {   

    UserImpl user = null;
    try {
      user = ofy().get(key);
    } catch (NotFoundException e) {
      user = null;
    }
    
    return updateAdministrator(user);
  }

  /**
   * Modify the information of a specific {@link User}.
   * 
   * @param updatedUser The updated information on the {@link User}.
   * @throws ObjectNotFoundException If the object is not found.
   * @throws InvalidEditException If the user cannot be edited because a non-editable field doesn't match.
   */
  public void modifyUser(
      final User updatedUser) throws ObjectNotFoundException, InvalidEditException {

    // TODO Validate that the current user has the required privileges!
    long userId = updatedUser.getId();

    Objectify ofyTxn = newOfyTransaction();
    try {
      UserImpl user = ofyTxn.get(UserImpl.class, userId);      
      user.editFrom(updatedUser);
      ofyTxn.put(user);
      ofyTxn.getTxn().commit();
    } catch (NotFoundException e) {
      throw new ObjectNotFoundException("User not found, can't modify. Id = " + userId);
    } finally {
      if (ofyTxn.getTxn().isActive()) {
        ofyTxn.getTxn().rollback();
      }
    }
  }

  /**
   * Access the user given an email, or create it if not found.
   * 
   * @param emailQuery The email of the user to get or create.
   * @param locale The locale to use for this user, if it needs to be created.
   * @return The {@link User} obtained from the given email.
   * @throws TransactionFailedException If the user cannot be created.
   */
  public User getUserByEmailOrCreate(
      final String emailQuery,
      final String locale) throws TransactionFailedException {

    if (emailQuery == null) {
      return null;
    }

    Objectify ofyTxn = newOfyTransaction();

    UserImpl user = null;
    int retry = 0;
    while (user == null && retry < MAX_RETRIES) {
      try {
        EmailToUser emailToUser = ofyTxn.get(EmailToUser.class, emailQuery);
        ofyTxn.getTxn().commit();
        user = ofy().get(emailToUser.getUserKey());
      } catch (NotFoundException e) {
        user = new UserImpl(emailQuery);
        user.setLocale(locale);
        ofy().put(user);
        EmailToUser emailToUser = new EmailToUser(emailQuery, user.createKey());
        ofyTxn.put(emailToUser);
        ofyTxn.getTxn().commit();
      } finally {
        if (ofyTxn.getTxn().isActive()) {
          ofyTxn.getTxn().rollback();
          if (user != null) {
            ofy().delete(user);
          }
          user = null;
        }
      }
      retry++;
    }
    
    if (retry == MAX_RETRIES) {
      throw new TransactionFailedException();
    }

    return updateAdministrator(user);
  }

  /**
   * Makes sure the passed {@link User} administrator field is set to 
   * the right value.
   * 
   * @param user The user on which to update the administrator field. Can be {@code null}.
   * @return The user with the valid administrator field, or {@code null} id {@code null} was passed in.
   */
  private User updateAdministrator(UserImpl user) {
    if (user == null) {
      return null;
    }
    if (user.getEmail().equals(ADMINISTRATOR_EMAIL)) {
      user.setAdministrator(true);
    } else {
      user.setAdministrator(false);
    }
    return user;
  }

  /**
   * Logout the currently logged-in user.
   */
  public void logoutSessionUser() {
    try {
      RelyingParty.getInstance().invalidate(request.get(), response.get());
    } catch (IOException exception) {
      logger.warning("RelyingParty logout failed" + exception.getMessage());
    }

    String sessionId = getSessionId();
    getOrCreateSession(sessionId).logoutUser();
    createInvalidSession(getSessionId());
  }

  /**
   * Get the ID of the current {@link HttpSession}.
   * 
   * @return The session id, a string.
   */
  private String getSessionId() {
    return session.get().getId();
  }  

  /**
   * Gets the session from the datastore/cache. If not found, creates an
   * invalid session.
   * 
   * @param sessionId The id of the session to fetch or create.
   * @return The newly fetched (or created) {@link Session}.
   */
  private Session getOrCreateSession(String sessionId) {
    Session currentSession = null;
    try {
      currentSession = ofy().get(Session.class, sessionId);
    } catch (NotFoundException e) {
      currentSession = createInvalidSession(sessionId);
    }
    assert currentSession != null : "Session not found, and unable to create it!";
    return currentSession;
  }

  /**
   * Call this when the session is known to be invalid, in order to store it
   * with a null user key. Future queries will be faster.
   * 
   * @param sessionId The id of the invalid session.
   * @return The newly created invalid {@link Session}.
   */
  private Session createInvalidSession(String sessionId) {
    Session currentSession = new Session(sessionId, null);
    ofy().put(currentSession);
    return currentSession;
  }

}
