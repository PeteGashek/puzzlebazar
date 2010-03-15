package com.puzzlebazar.server.handler;

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.philbeaudoin.platform.dispatch.server.ActionHandler;
import com.philbeaudoin.platform.dispatch.server.ExecutionContext;
import com.philbeaudoin.platform.dispatch.shared.ActionException;
import com.philbeaudoin.platform.dispatch.shared.ServiceException;

import com.puzzlebazar.server.currentuser.CurrentUserManager;
import com.puzzlebazar.shared.action.GetUserInfo;
import com.puzzlebazar.shared.action.GetUserInfoResult;

public class GetUserInfoHandler implements ActionHandler<GetUserInfo, GetUserInfoResult> {

  private final Logger logger;
  private final CurrentUserManager currentUserManager;

  @Inject
  public GetUserInfoHandler(
      final Logger logger,
      final CurrentUserManager currentUserManager ) {

    this.logger = logger;
    this.currentUserManager = currentUserManager;    
  }

  @Override
  public GetUserInfoResult execute(
      final GetUserInfo action,
      final ExecutionContext context ) throws ActionException, ServiceException {
    try {
      return new GetUserInfoResult( currentUserManager.get() );
    }
    catch (Exception cause) {
      // TODO: The logging should move to some other place where the Service Exception is caught.
      logger.severe( "Service exception: " + cause.getMessage() );
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
