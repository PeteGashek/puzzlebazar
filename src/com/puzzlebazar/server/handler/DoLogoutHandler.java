package com.puzzlebazar.server.handler;


import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;

import com.philbeaudoin.gwt.dispatch.server.ActionHandler;
import com.philbeaudoin.gwt.dispatch.server.ExecutionContext;
import com.philbeaudoin.gwt.dispatch.shared.ActionException;
import com.puzzlebazar.shared.action.DoLogout;
import com.puzzlebazar.shared.action.StringResult;


public class DoLogoutHandler implements ActionHandler<DoLogout, StringResult> {
  
  private final UserService userService;

  @Inject
  public DoLogoutHandler(
      final UserService userService ) {
    this.userService = userService;
  }

  @Override
  public StringResult execute(final DoLogout action,
      final ExecutionContext context) throws ActionException {
    try {
      return new StringResult( userService.createLogoutURL(action.getCurrentURL()) );
    }
    catch (Exception cause) {
      throw new ActionException(cause);
    }
  }

  @Override
  public void rollback(final DoLogout action,
      final StringResult result,
      final ExecutionContext context) throws ActionException {
    // Nothing to do here
  }

  @Override
  public Class<DoLogout> getActionType() {
    return DoLogout.class;
  }
}
