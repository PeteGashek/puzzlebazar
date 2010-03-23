package com.puzzlebazar.server.handler;

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.dispatch.server.ActionHandler;
import com.philbeaudoin.gwtp.dispatch.server.ExecutionContext;
import com.philbeaudoin.gwtp.dispatch.shared.ActionException;

import com.puzzlebazar.server.currentuser.CurrentUserManager;
import com.puzzlebazar.shared.action.GetCurrentUser;
import com.puzzlebazar.shared.action.GetUserResult;

public class GetCurrentUserHandler implements ActionHandler<GetCurrentUser, GetUserResult> {

  private final CurrentUserManager currentUserManager;

  @Inject
  public GetCurrentUserHandler(
      final CurrentUserManager currentUserManager ) {

    this.currentUserManager = currentUserManager;    
  }

  @Override
  public GetUserResult execute(
      final GetCurrentUser action,
      final ExecutionContext context ) throws ActionException {
    return new GetUserResult( currentUserManager.get() );
  }

  @Override
  public void undo(final GetCurrentUser action,
      final GetUserResult result,
      final ExecutionContext context) throws ActionException {
    // Nothing to do here
  }

  @Override
  public Class<GetCurrentUser> getActionType() {
    return GetCurrentUser.class;
  }
}
