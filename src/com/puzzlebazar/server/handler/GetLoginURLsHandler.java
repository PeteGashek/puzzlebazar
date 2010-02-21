package com.puzzlebazar.server.handler;


import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;

import com.philbeaudoin.gwt.dispatch.server.ActionHandler;
import com.philbeaudoin.gwt.dispatch.server.ExecutionContext;
import com.philbeaudoin.gwt.dispatch.shared.ActionException;

import com.puzzlebazar.shared.action.GetLoginURLs;
import com.puzzlebazar.shared.action.GetLoginURLsResult;

public class GetLoginURLsHandler implements ActionHandler<GetLoginURLs, GetLoginURLsResult> {
  
  private final UserService userService;

  @Inject
  public GetLoginURLsHandler(
      final UserService userService ) {
    this.userService = userService;
  }

  @Override
  public GetLoginURLsResult execute(final GetLoginURLs action,
      final ExecutionContext context) throws ActionException {
    try {
      String loginURL = userService.createLoginURL(action.getHref());
      String logoutURL = userService.createLogoutURL(action.getHref());      
      
      return new GetLoginURLsResult(loginURL, logoutURL);
    }
    catch (Exception cause) {
      throw new ActionException(cause);
    }
  }

  @Override
  public void rollback(final GetLoginURLs action,
      final GetLoginURLsResult result,
      final ExecutionContext context) throws ActionException {
    // Nothing to do here
  }

  @Override
  public Class<GetLoginURLs> getActionType() {
    return GetLoginURLs.class;
  }
}
