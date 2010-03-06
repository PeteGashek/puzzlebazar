package com.puzzlebazar.server.handler;

import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.dispatch.server.ActionHandler;
import com.philbeaudoin.gwt.dispatch.server.ExecutionContext;
import com.philbeaudoin.gwt.dispatch.server.Util;
import com.philbeaudoin.gwt.dispatch.shared.ActionException;
import com.philbeaudoin.gwt.dispatch.shared.ServiceException;

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

      User userInfo = new User();
      PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
      try {
        try {
          userInfo = persistenceManager.getObjectById(User.class, appengineUser.getEmail());
        } catch( JDOObjectNotFoundException exception ) {
          // User is authenticated via Google User Service, but it is not in the database yet,
          // copy known information.
          userInfo.setEmail( appengineUser.getEmail() );
          userInfo.setNickname( appengineUser.getNickname() );
          persistenceManager.makePersistent( userInfo );
        } 
      }
      finally {
        userInfo.setAuthenticated( true );
        userInfo.setAdministrator( userService.isUserAdmin() );
        persistenceManager.close();
      }
      
      return new GetUserInfoResult(userInfo);
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
