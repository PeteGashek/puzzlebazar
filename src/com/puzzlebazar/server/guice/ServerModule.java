package com.puzzlebazar.server.guice;

import com.philbeaudoin.platform.dispatch.server.guice.ActionHandlerModule;

import com.puzzlebazar.server.handler.LogoutHandler;
import com.puzzlebazar.server.handler.GetUserInfoHandler;
import com.puzzlebazar.shared.action.Logout;
import com.puzzlebazar.shared.action.GetUser;

/**
 * Module which binds the handlers and configurations
 *
 */
public class ServerModule extends ActionHandlerModule {

  @Override
  protected void configureHandlers() {
    bindHandler(Logout.class, LogoutHandler.class);
    bindHandler(GetUser.class, GetUserInfoHandler.class);
  }
}
