package com.puzzlebazar.server.guice;

import com.philbeaudoin.gwt.dispatch.server.guice.ActionHandlerModule;

import com.puzzlebazar.server.handler.LoginHandler;
import com.puzzlebazar.shared.action.Login;

/**
 * Module which binds the handlers and configurations
 *
 */
public class ServerModule extends ActionHandlerModule {

  @Override
  protected void configureHandlers() {
    bindHandler(Login.class, LoginHandler.class);
  }
}
