package com.puzzlebazar.server.handler;


import com.google.inject.Inject;

import com.philbeaudoin.platform.dispatch.server.ActionHandler;
import com.philbeaudoin.platform.dispatch.server.ExecutionContext;
import com.philbeaudoin.platform.dispatch.shared.ActionException;
import com.puzzlebazar.server.currentuser.CurrentUserManager;
import com.puzzlebazar.shared.action.Logout;
import com.puzzlebazar.shared.action.NoResult;


public class LogoutHandler implements ActionHandler<Logout, NoResult> {
  
  private final CurrentUserManager currentUserManager;

  @Inject
  public LogoutHandler(
      final CurrentUserManager currentUserManager ) {
    this.currentUserManager = currentUserManager;
  }

  @Override
  public NoResult execute(final Logout action,
      final ExecutionContext context) throws ActionException {
    try {
      currentUserManager.logout();
      return null;
    }
    catch (Exception cause) {
      throw new ActionException(cause);
    }
  }

  @Override
  public void rollback(final Logout action,
      final NoResult result,
      final ExecutionContext context) throws ActionException {
    // Nothing to do here
  }

  @Override
  public Class<Logout> getActionType() {
    return Logout.class;
  }
}
