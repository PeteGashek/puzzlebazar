package com.puzzlebazar.server.handler;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;
import com.google.inject.Provider;

import com.philbeaudoin.gwt.dispatch.server.ActionHandler;
import com.philbeaudoin.gwt.dispatch.server.ExecutionContext;
import com.philbeaudoin.gwt.dispatch.shared.ActionException;

import com.puzzlebazar.server.datastore.UserInfoJdo;
import com.puzzlebazar.shared.action.GetUserInfo;
import com.puzzlebazar.shared.action.GetUserInfoResult;
import com.puzzlebazar.shared.model.UserInfo;

public class GetUserInfoHandler implements ActionHandler<GetUserInfo, GetUserInfoResult> {

  private final UserService userService;
  private final Provider<PersistenceManager> persistenceManager;
  
  @Inject
  public GetUserInfoHandler(
      final UserService userService,
      final Provider<PersistenceManager> persistenceManager ) {
    this.userService = userService;
    this.persistenceManager = persistenceManager;
  }

  @Override
  public GetUserInfoResult execute(final GetUserInfo action,
      final ExecutionContext context ) throws ActionException {
    try {

      User user = userService.getCurrentUser();      

      if( user == null ) return null;

      UserInfo userInfo = new UserInfo();
      
      // User is authenticated via Google UserService
      userInfo.setAuthenticated( true );

      UserInfoJdo jdo;
      try {
        jdo = persistenceManager.get().getObjectById(UserInfoJdo.class, user.getEmail());
        jdo.to( userInfo );
      } catch( JDOObjectNotFoundException exception ) {
        // User is authenticated via google User Service, but it is not in the database yet,
        // copy known information.
        userInfo.setEmail( user.getEmail() );
        userInfo.setNickname( user.getNickname() );
        userInfo.setAdministrator( userService.isUserAdmin() );
      }
      finally {
        persistenceManager.get().close();
      }

      return new GetUserInfoResult(userInfo);
    }
    catch (Exception cause) {
      throw new ActionException(cause);
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
