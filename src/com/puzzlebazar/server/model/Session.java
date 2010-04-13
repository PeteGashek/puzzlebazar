package com.puzzlebazar.server.model;

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


import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jsr107cache.Cache;


import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.dyuproject.openid.ext.AxSchemaExtension;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.puzzlebazar.shared.model.User;
import com.puzzlebazar.shared.model.UserImpl;
import com.puzzlebazar.shared.util.AvailableLocales;

/**
 * Stores information on the current session.
 * 
 * @author Philippe Beaudoin
 */
@PersistenceCapable
public class Session implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1433528124645869019L;

  @SuppressWarnings("unused")
  @PrimaryKey
  @Persistent
  private String decoratedSessionId;

  @Persistent
  private String currentUserKey;

  @SuppressWarnings("unused")
  private Session() {
    // For serialization only
  }
  
  public Session(String decoratedSessionId, String currentUserKey) {
    this.decoratedSessionId = decoratedSessionId;
    this.currentUserKey = currentUserKey;
  }

  /**
   * @return The attached {@link UserImpl}'s key, encoded as a string.
   */
  public String getCurrentUserKey() {
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
  public static class Manager {

    private static final String SESSION_TOKEN = "SESSION-";

    private final Logger logger;
    private final Cache cache;
    private final PersistenceManagerFactory persistenceManagerFactory;
    private final UserImplServer.Manager userManager;
    private final AvailableLocales availableLocales;
    private final Provider<HttpSession> session;
    private final Provider<HttpServletRequest> request;
    private final Provider<HttpServletResponse> response;
    
    @Inject
    public Manager(
        final Logger logger,
        final Cache cache, 
        final PersistenceManagerFactory persistenceManagerFactory,
        final UserImplServer.Manager userManager,
        final AvailableLocales availableLocales,
        final Provider<HttpSession> session,
        final Provider<HttpServletRequest> request,
        final Provider<HttpServletResponse> response ) {
      this.logger = logger;
      this.cache = cache;
      this.persistenceManagerFactory = persistenceManagerFactory;
      this.userManager = userManager;
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
    public User fetchUser() {
      String sessionKey = getSessionKey();

      Session session = (Session)cache.get( sessionKey );
      if( session == null ) {
        PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
        try {
          session = persistenceManager.getObjectById( Session.class, sessionKey );
        }
        catch( JDOObjectNotFoundException exception ) {
          session = null;
        }
        finally {
          persistenceManager.close();
        }
      }

      String userKey = null;
      if( session != null ) {
        userKey = session.getCurrentUserKey();
        // Special case: A null user key means the user is known not to be logged-in
        //               this saves a trip to the datastore.
        if( userKey == null )
          return null;
      }
      User user = userManager.fetch(userKey);
      if( user == null )
        markInvalidUser( sessionKey );

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

      User user = userManager.fetchByEmailOrCreate(email, locale);
      if( user != null ) {
        PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
        try {
          String key = getSessionKey();
          String userKey = user.getKey();
          Session session = new Session( key, userKey );
          cache.put( key, session );
          persistenceManager.makePersistent( session );
        }
        finally {
          persistenceManager.close();
        }
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

      String sessionKey = getSessionKey();

      // Store a special user with a null password in the cache so that we save
      // trips to the datastore on future requests.
      markInvalidUser( sessionKey );

      // Remove the user from the session
      PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
      try {
        Session session = persistenceManager.getObjectById( Session.class, sessionKey );
        session.logoutUser();
      }
      catch( JDOObjectNotFoundException exception ) {
      }
      finally {
        persistenceManager.close();
      }
    }


    /**
     * Store a special user with a null password in the cache so that we save
     * trips to the datastore on future requests.
     * 
     * @param sessionKey The session key
     */
    private void markInvalidUser(String sessionKey) {
      cache.put( sessionKey, new Session(sessionKey, null) );      
    }

    private String getSessionKey() {
      return SESSION_TOKEN + session.get().getId();
    }  
  }
  

}
