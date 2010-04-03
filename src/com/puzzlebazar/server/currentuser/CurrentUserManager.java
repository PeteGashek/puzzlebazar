package com.puzzlebazar.server.currentuser;

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
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jsr107cache.Cache;

import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.dyuproject.openid.ext.AxSchemaExtension;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.puzzlebazar.server.model.Session;
import com.puzzlebazar.shared.model.User;
import com.puzzlebazar.shared.util.AvailableLocales;

/**
 * This class manages the user currently logged into the session
 * 
 * @author Philippe Beaudoin
 */
@Singleton
public class CurrentUserManager {

  public static final String SESSION_TOKEN = "SESSION-";
  public static final String USER_TOKEN = "USER-";

  private static final Object ADMINISTRATOR_EMAIL = "philippe.beaudoin@gmail.com";

  private final Logger logger;
  private final AvailableLocales availableLocales;
  private final PersistenceManagerFactory persistenceManagerFactory;
  private final Provider<Cache> cache;
  private final Provider<HttpSession> session;
  private final Provider<HttpServletRequest> request;
  private final Provider<HttpServletResponse> response;

  @Inject
  public CurrentUserManager(
      final Logger logger,
      final AvailableLocales availableLocales,
      final PersistenceManagerFactory persistenceManagerFactory,
      final Provider<Cache> cache,
      final Provider<HttpSession> session,
      final Provider<HttpServletRequest> request,
      final Provider<HttpServletResponse> response ) {
    this.logger = logger;
    this.availableLocales = availableLocales;
    this.persistenceManagerFactory = persistenceManagerFactory;
    this.cache = cache;
    this.session = session;
    this.request = request;
    this.response = response;
  }

  /**
   * Access the user currently logged into this session, or {@code null} if 
   * not logged in.
   * 
   * @return The {@link User} currently logged-in, or {@code null} if no user is logged in.
   */
  public User get() {
    String sessionKey = SESSION_TOKEN + session.get().getId();

    Session session = (Session)cache.get().get( sessionKey );
    User user = null;
    if( session != null ) {
      // Special case: A null user key means the user is known not to be logged-in
      //               this saves a trip to the datastore.
      String userKey = session.getCurrentUserKey();
      if( userKey == null )
        return null;
      user = (User)cache.get().get( USER_TOKEN + userKey );

      if( user != null )
        return user;
    }

    PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
    try {
      session = persistenceManager.getObjectById( Session.class, sessionKey );
      String userKey = session.getCurrentUserKey();
      if( userKey != null )
        user = persistenceManager.getObjectById( User.class, userKey );      
    }
    catch( JDOObjectNotFoundException exception ) {
      user = null;
    }
    finally {
      persistenceManager.close();
    }

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
  public void set(OpenIdUser openIdUser) {

    Map<String,String> axSchema = AxSchemaExtension.get(openIdUser);
    String email = axSchema.get("email");
    String language = axSchema.get("language");
    if( email == null ) {
      logger.warning( "Setting a user with a null email. Call logout instead?" );
      return;
    }

    User user = null;
    PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
    Transaction transaction = persistenceManager.currentTransaction();
    try {        

      // Fetches the user from the datastore. Creates it if needed.
      final Query query = persistenceManager.newQuery( User.class );  
      query.setUnique( true );
      query.setFilter( "email==\"" + email + "\"" );

      transaction.begin();        
      user = (User)query.execute();
      if( user == null ) {
        // User is authenticated via an OpenId provider, but it is not in the datastore yet,
        // copy known information.

        user = new User( email );
        user.setLocale( availableLocales.getBestLocale(language).getLocale() );
        persistenceManager.makePersistent( user );
      }
      transaction.commit();
      transaction.begin();        
      // Attaches the user to the session in the datastore and in the cache
      String key = SESSION_TOKEN + session.get().getId();
      String userKey = user.getKey();
      Session session = new Session( key, userKey ); 
      cache.get().put( key, session );
      cache.get().put( USER_TOKEN + userKey, user );
      persistenceManager.makePersistent( session );     

      transaction.commit();        
    }
    finally {
      if( transaction.isActive() )
        transaction.rollback();

      assert user != null : "Unexpected null user. Datastore error?";
      user.setAuthenticated( true );
      user.setAdministrator( ADMINISTRATOR_EMAIL.equals(email) );
      persistenceManager.close();
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

    String sessionKey = SESSION_TOKEN + session.get().getId();
    
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
    cache.get().put( sessionKey, new Session(sessionKey, null) );      
  }

}