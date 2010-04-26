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
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.Id;
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
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.helper.DAOBase;
import com.puzzlebazar.shared.model.User;
import com.puzzlebazar.shared.model.UserImpl;
import com.puzzlebazar.shared.util.AvailableLocales;

/**
 * Stores information on the current session.
 * 
 * @author Philippe Beaudoin
 */
@Cached
public class Session implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1433528124645869019L;

  @SuppressWarnings("unused")
  @Id private String sessionId;
  private Key<UserImpl> currentUserKey;

  @SuppressWarnings("unused")
  private Session() {
    // For serialization/Objectify only
  }
  
  public Session(String sessionId, Key<UserImpl> currentUserKey) {
    this.sessionId = sessionId;
    this.currentUserKey = currentUserKey;
  }

  /**
   * @return The attached {@link UserImpl}'s key.
   */
  public Key<UserImpl> getCurrentUserKey() {
    return currentUserKey;
  }

  /**
   * Logout the user from the current session. This is done by setting the
   * {@code currentUserKey} to {@code null}.
   */
  public void logoutUser() {
    currentUserKey = null;
  }
  

  /**
   * The class responsible of managing cache and datastore
   * storage of {@link Session} objects.
   * 
   * @author Philippe Beaudoin
   */
  public static class DAO extends DAOBase {
    
    static {
      ObjectifyService.register(Session.class);
    }
    
    private final Logger logger;
    private final UserDAO userDAO;
    private final AvailableLocales availableLocales;
    private final Provider<HttpSession> session;
    private final Provider<HttpServletRequest> request;
    private final Provider<HttpServletResponse> response;
    
    @Inject
    public DAO(
        final Logger logger,
        final UserDAO userDAO,
        final AvailableLocales availableLocales,
        final Provider<HttpSession> session,
        final Provider<HttpServletRequest> request,
        final Provider<HttpServletResponse> response ) {
      this.logger = logger;
      this.userDAO = userDAO;
      this.availableLocales = availableLocales;
      this.session = session;
      this.request = request;
      this.response = response;
    }

    /**
     * Access the user currently logged into the session, or {@code null} if 
     * not logged in.
     * 
     * @return The {@link User} currently logged-in, or {@code null} if no user is logged in.
     */
    public User getUser() {
      String sessionId = getSessionId();

      Session session = getOrCreateSession(sessionId);
      
      Key<UserImpl> userKey = session.getCurrentUserKey();
      
      // Special case: A null user key means the user is known not to be logged-in
      //               this saves a trip to the datastore.
      if( userKey == null )
        return null;
      
      User user = userDAO.getUser(userKey);
      if( user == null )
        createInvalidSession( sessionId );
      else
        ((UserImpl)user).setAuthenticated(true);

      return user;
    }

    
    /**
     * Sets the user currently logged into the session. This should
     * be called by the OpenId servlet when the OpenId provider 
     * authenticates the user.
     * 
     * @param openIdUser The {@link OpenIdUser} currently logged into the session.
     */
    public void setUser(OpenIdUser openIdUser) {

      Map<String,String> axSchema = AxSchemaExtension.get(openIdUser);
      String email = axSchema.get("email");
      if( email == null ) {
        logger.warning( "Setting a user with a null email. Call logout instead?" );
        return;
      }
      String locale =  availableLocales.getBestLocale(axSchema.get("language")).getLocale();

      User user = userDAO.getUserByEmailOrCreate(email, locale);
      if( user != null ) {
        String sessionId = getSessionId();
        ofy().put( new Session( sessionId, user.createKey() ) );
      }
    }

    /**
     * Logout the currently logged-in user.
     */
    public void logout() {
      try {
        RelyingParty.getInstance().invalidate(request.get(), response.get());
      } catch (IOException exception) {
        logger.warning( "RelyingParty logout failed" + exception.getMessage() );
      }

      String sessionId = getSessionId();
      Session session = getOrCreateSession( sessionId );
      session.logoutUser();
      createInvalidSession( getSessionId() );
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
      Session session = null;
      try {
        session = ofy().get( Session.class, sessionId );
      }
      catch( NotFoundException e ) {
        session = createInvalidSession( sessionId );
      }
      assert session != null : "Session not found, and unable to create it!";
      return session;
    }

    /**
     * Call this when the session is known to be invalid, in order to store it
     * with a null user key. Future queries will be faster.
     * 
     * @param sessionId The id of the invalid session.
     * @return The newly created invalid {@link Session}.
     */
    private Session createInvalidSession( String sessionId ) {
      Session session = new Session(sessionId, null);
      ofy().put( session );
      return session;
    }  
  }
  

}
