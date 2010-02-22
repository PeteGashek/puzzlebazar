package com.puzzlebazar.server.handler;


import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;

import com.philbeaudoin.gwt.dispatch.server.ActionHandler;
import com.philbeaudoin.gwt.dispatch.server.ExecutionContext;
import com.philbeaudoin.gwt.dispatch.shared.ActionException;
import com.puzzlebazar.shared.action.DoLogin;
import com.puzzlebazar.shared.action.StringResult;


public class DoLoginHandler implements ActionHandler<DoLogin, StringResult> {
  
  private final UserService userService;

  @Inject
  public DoLoginHandler(
      final UserService userService ) {
    this.userService = userService;
  }

  @Override
  public StringResult execute(final DoLogin action,
      final ExecutionContext context) throws ActionException {
    try {
      return new StringResult( userService.createLoginURL(action.getCurrentURL()) );
    }
    catch (Exception cause) {
      throw new ActionException(cause);
    }
  }

  @Override
  public void rollback(final DoLogin action,
      final StringResult result,
      final ExecutionContext context) throws ActionException {
    // Nothing to do here
  }

  @Override
  public Class<DoLogin> getActionType() {
    return DoLogin.class;
  }
}
