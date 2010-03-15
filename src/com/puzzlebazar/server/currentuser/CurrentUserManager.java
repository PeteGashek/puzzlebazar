package com.puzzlebazar.server.currentuser;

import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.servlet.http.HttpSession;

import net.sf.jsr107cache.Cache;

import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.ext.AxSchemaExtension;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.puzzlebazar.server.model.Session;
import com.puzzlebazar.shared.model.User;

/**
 * This class manages the user currently logged into the session
 * 
 * @author Philippe Beaudoin
 */
@Singleton
public class CurrentUserManager {

  private static final String USER_TOKEN = "USER-";

  private static final Object ADMINISTRATOR_EMAIL = "philippe.beaudoin@gmail.com";

  private final Logger logger;
  private final PersistenceManagerFactory persistenceManagerFactory;
  private final Provider<Cache> cache;
  private final Provider<HttpSession> session;
  
  @Inject
  public CurrentUserManager(
      final Logger logger,
      final PersistenceManagerFactory persistenceManagerFactory,
      final Provider<Cache> cache,
      final Provider<HttpSession> session) {

    this.logger = logger;
    this.persistenceManagerFactory = persistenceManagerFactory;
    this.cache = cache;
    this.session = session;
  }
  
  /**
   * Access the user currently logged into this session, or {@code null} if 
   * not logged in.
   * 
   * @return The {@link User} currently logged-in, or {@code null} if no user is logged in.
   */
  public User get() {
    String key = USER_TOKEN + session.get().getId();
    
    User user = (User)cache.get().get( key );
    if( user != null ) {
      // Special case: A null email means the user is known not to be logged-in
      //               this saves a trip to the datastore.
      if( user.getEmail() == null )
        return null;
      
      return user;
    }
    
    PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
    try {
      Session session = persistenceManager.getObjectById( Session.class, key );
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

    if( user == null ) {
      // Store a special user with a null password in the cache so that we save
      // trips to the datastore on future requests.
      cache.get().put( key, new User(null) );      
    }
    
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
        // TODO Set other fields in the user base on what we can get from the OpenId Provider
        persistenceManager.makePersistent( user );
      }

      // Attaches the user to the session in the datastore and in the cache
      String key = USER_TOKEN + session.get().getId();
      cache.get().put( key, user );
      Session sessionUser = new Session( key, user.getKey() ); 
      persistenceManager.makePersistent( sessionUser );     
      
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
    String key = USER_TOKEN + session.get().getId();
    
    // Store a special user with a null password in the cache so that we save
    // trips to the datastore on future requests.
    cache.get().put( key, new User(null) );

    // Remove the user from the session
    PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
    try {
      Session session = persistenceManager.getObjectById( Session.class, key );
      session.logoutUser();
    }
    catch( JDOObjectNotFoundException exception ) {
    }
    finally {
      persistenceManager.close();
    }
  }      
    
}