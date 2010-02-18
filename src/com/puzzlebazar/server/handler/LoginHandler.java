package com.puzzlebazar.server.handler;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;

import com.philbeaudoin.gwt.dispatch.server.ActionHandler;
import com.philbeaudoin.gwt.dispatch.server.ExecutionContext;
import com.philbeaudoin.gwt.dispatch.shared.ActionException;

import com.puzzlebazar.shared.action.Login;
import com.puzzlebazar.shared.action.LoginResult;
import com.puzzlebazar.shared.model.LoginInfo;

public class LoginHandler implements ActionHandler<Login, LoginResult> {
  private final Provider<HttpServletRequest> servletRequest;

  @Inject
  public LoginHandler(
      final Provider<HttpServletRequest> servletRequest) {
    this.servletRequest = servletRequest;
  }

  @Override
  public LoginResult execute(final Login action,
      final ExecutionContext context) throws ActionException {
    final String name = action.getName();

    try {
      LoginInfo loginInfo = new LoginInfo();
      
      loginInfo.setUsername( name );
      loginInfo.setUseragent( servletRequest.get().getHeader("User-Agent") );
      
      return new LoginResult(loginInfo);
    }
    catch (Exception cause) {
      throw new ActionException(cause);
    }
  }

  @Override
  public void rollback(final Login action,
      final LoginResult result,
      final ExecutionContext context) throws ActionException {
    // Nothing to do here
  }

  @Override
  public Class<Login> getActionType() {
    return Login.class;
  }
}
