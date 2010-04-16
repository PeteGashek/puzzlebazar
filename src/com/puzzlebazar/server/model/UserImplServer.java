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

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.PersistenceCapable;

import net.sf.jsr107cache.Cache;

import com.google.inject.Inject;
import com.puzzlebazar.shared.ObjectNotFoundException;
import com.puzzlebazar.shared.model.InvalidEditException;
import com.puzzlebazar.shared.model.User;
import com.puzzlebazar.shared.model.UserImpl;

/**
 * The server version of {@link UserImpl}. This class 
 * stores keys to linked objects and offers fetch methods 
 * to get them from the datastore or the cache.
 * 
 * @author Philippe Beaudoin
 */
@PersistenceCapable
public class UserImplServer extends UserImpl {  

  private static final long serialVersionUID = -7048613221853791612L;


  public UserImplServer(String email) {
    super(email);
  }
  
  
  /**
   * The class responsible of managing cache and datastore
   * storage of {@link UserImplServer} objects.
   * 
   * @author Philippe Beaudoin
   */
  public static class Manager {
    
    private static final String USER_TOKEN = "USER-";
    private static final Object ADMINISTRATOR_EMAIL = "philippe.beaudoin@gmail.com";
        
    private final Cache cache;
    private final PersistenceManagerFactory persistenceManagerFactory;
    
    @Inject
    public Manager(
        final Cache cache, 
        final PersistenceManagerFactory persistenceManagerFactory) {
      this.cache = cache;
      this.persistenceManagerFactory = persistenceManagerFactory;
    }

    /**
     * Access the user given a user key. If the user is available in the cache 
     * then it will be taken from there, otherwise it will be taken from the datastore.
     * Returns {@code null} if the user is not found or if a {@code null} key is 
     * passed. If the user is found in the datastore, it is saved in the cache.
     * 
     * @param userKey The key for the user to fetch.
     * @return The {@link User} obtained from the cache or the datastore, or {@code null} if none found.
     */
    public User fetch(
        final String userKey) {
      return fetch(userKey, true);
    }
    
    /**
     * Access the user given a user key. The user will always be taken from the datastore.
     * Returns {@code null} if the user is not found or if a {@code null} key is 
     * passed. If the user is found in the datastore, it is saved in the cache.
     * 
     * @param userKey The key for the user to fetch.
     * @return The {@link User} obtained from the datastore, or {@code null} if none found.
     */
    public User fetchFromDatastore(
        final String userKey) {
      return fetch(userKey, false);
    }
    
    /**
     * Access the user given a user key. If the user is available in the cache and
     * the {@code useCache} flag is {@code true} then it
     * will be taken from there, otherwise it will be taken from the datastore.
     * Returns {@code null} if the user is not found or if a {@code null} key is 
     * passed. If the user is found in the datastore, it is saved in the cache.
     * 
     * @param userKey The key for the user to fetch.
     * @param useCache {@code true} to use cache, false otherwise.
     * @return The {@link User} obtained from the cache or the datastore, or {@code null} if none found.
     */
    private User fetch(
        final String userKey,
        final boolean useCache) {
      
      if( userKey == null )
        return null;
      
      User user = null;
      if( useCache )
        user = (User)cache.get( USER_TOKEN + userKey );
      if( user != null )
        return user;
      PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
      try {
        if( userKey != null )
          user = new UserImpl( persistenceManager.getObjectById( UserImplServer.class, userKey ) );      
      }
      catch( JDOObjectNotFoundException exception ) {
        user = null;
      }
      finally {
        persistenceManager.close();
      }
      
      saveToCache(cache, user);
      
      return user;
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
      String userKey = updatedUser.getKey();
      if( userKey == null )
        throw new ObjectNotFoundException( "Cannot modify null user." );

      PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
      Transaction transaction = persistenceManager.currentTransaction();
      try {
        transaction.begin();
        UserImplServer user = persistenceManager.getObjectById( UserImplServer.class, userKey );      
        user.editFrom( updatedUser );
        persistenceManager.makePersistent( user );
        cache.remove( USER_TOKEN + userKey );
        transaction.commit();
      }
      catch( JDOObjectNotFoundException exception ) {
        throw new ObjectNotFoundException( "User not found, can't modify. Key = " + userKey );
        
      }
      finally {
        if( transaction.isActive() )
          transaction.rollback();
        persistenceManager.close();
      }      
      
    }
    
    /**
     * Access the user given an email. The user is always taken from the datastore,
     * but the it is then stored in the cache.
     * 
     * @param email The email of the user to fetch or create.
     * @param locale The locale to use for this user, if it needs to be created.
     * @return The {@link User} obtained from the given email.
     */
    public User fetchByEmailOrCreate(
        final String email,
        final String locale ) {
      
      if( email == null )
        return null;

      PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
      Transaction transaction = persistenceManager.currentTransaction();
      UserImplServer user = null;
      try {        
        // Fetches the user from the datastore. Creates it if needed.
        final Query query = persistenceManager.newQuery( UserImplServer.class );  
        query.setUnique( true );
        query.setFilter( "email==\"" + email + "\"" );

        transaction.begin();        
        user = (UserImplServer)query.execute();
        if( user == null ) {
          // User is authenticated via an OpenId provider, but it is not in the datastore yet,
          // copy known information.

          user = new UserImplServer( email );
          user.setLocale( locale );
          persistenceManager.makePersistent( user );
        }
        user.setAuthenticated( true );
        user.setAdministrator( ADMINISTRATOR_EMAIL.equals(user.getEmail()) );
        transaction.commit();
      }
      finally {
        if( transaction.isActive() )
          transaction.rollback();

        assert user != null : "Unexpected null user. Datastore error?";
        persistenceManager.close();
      }

      if( user != null )
        return new UserImpl(user);
      
      return null;
    }

    /**
     * Makes sure the passed user is saved to the cache.
     * 
     * @param cache The {@link Cache}.
     * @param user The {@link User} to save, nothing will be save if it's {@code null}.
     */
    private void saveToCache(Cache cache, User user) {
      if( user == null )
        return;
      cache.put( USER_TOKEN + user.getKey(), user );
    }

  }

}
