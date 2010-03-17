package com.puzzlebazar.server.guice;

import com.philbeaudoin.platform.dispatch.server.guice.ActionHandlerModule;

import com.puzzlebazar.server.handler.EditUserHandler;
import com.puzzlebazar.server.handler.LogoutHandler;
import com.puzzlebazar.server.handler.GetCurrentUserHandler;
import com.puzzlebazar.shared.action.EditUser;
import com.puzzlebazar.shared.action.Logout;
import com.puzzlebazar.shared.action.GetCurrentUser;

/**
 * Module which binds the handlers and configurations
 *
 */
public class ServerModule extends ActionHandlerModule {

  @Override
  protected void configureHandlers() {
    bindHandler(Logout.class, LogoutHandler.class);
    bindHandler(GetCurrentUser.class, GetCurrentUserHandler.class);
    bindHandler(EditUser.class, EditUserHandler.class);
  }
}
