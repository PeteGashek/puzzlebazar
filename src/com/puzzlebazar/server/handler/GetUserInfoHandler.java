package com.puzzlebazar.server.handler;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;
import com.philbeaudoin.platform.dispatch.server.ActionHandler;
import com.philbeaudoin.platform.dispatch.server.ExecutionContext;
import com.philbeaudoin.platform.dispatch.server.Util;
import com.philbeaudoin.platform.dispatch.shared.ActionException;
import com.philbeaudoin.platform.dispatch.shared.ServiceException;

import com.puzzlebazar.shared.action.GetUserInfo;
import com.puzzlebazar.shared.action.GetUserInfoResult;
import com.puzzlebazar.shared.model.User;

public class GetUserInfoHandler implements ActionHandler<GetUserInfo, GetUserInfoResult> {

  private final UserService userService;
  private final PersistenceManagerFactory persistenceManagerFactory;
  private final Logger log;

  @Inject
  public GetUserInfoHandler(
      final UserService userService,
      final PersistenceManagerFactory persistenceManagerFactory,
      final Logger log) {
    this.userService = userService;
    this.persistenceManagerFactory = persistenceManagerFactory;    
    this.log = log;
  }

  @Override
  public GetUserInfoResult execute(
      final GetUserInfo action,
      final ExecutionContext context ) throws ActionException, ServiceException {
    try {

      com.google.appengine.api.users.User appengineUser = 
        userService.getCurrentUser();      

      if( appengineUser == null ) 
        return null;

      User user = null;
      PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
      Transaction transaction = persistenceManager.currentTransaction();
      try {        
        final Query query = persistenceManager.newQuery( User.class );  
        query.setUnique( true );
        query.setFilter( "email==\"" + appengineUser.getEmail() + "\"" );

        transaction.begin();        
        user = (User)query.execute();
        if( user == null ) {
          // User is authenticated via Google User Service, but it is not in the database yet,
          // copy known information.
          
          user = new User( appengineUser.getEmail() );
          user.setNickname( appengineUser.getNickname() );
          persistenceManager.makePersistent( user );
        }
        transaction.commit();        
      }
      finally {
        if( transaction.isActive() )
          transaction.rollback();
        
        assert user != null : "Unexpected null user. Datastore error?";
        user.setAuthenticated( true );
        user.setAdministrator( userService.isUserAdmin() );
        persistenceManager.close();
      }

      return new GetUserInfoResult(user);
    }
    catch (Exception cause) {
      Util.logException( log, this, cause );
      throw new ServiceException(cause);
    }
  }

  @Override
  public void rollback(final GetUserInfo action,
      final GetUserInfoResult result,
      final ExecutionContext context) throws ActionException {
    // Nothing to do here
  }

  @Override
  public Class<GetUserInfo> getActionType() {
    return GetUserInfo.class;
  }
}
