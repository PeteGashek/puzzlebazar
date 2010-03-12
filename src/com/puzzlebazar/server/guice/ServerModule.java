package com.puzzlebazar.server.guice;

import com.philbeaudoin.platform.dispatch.server.guice.ActionHandlerModule;

import com.puzzlebazar.server.handler.DoLoginHandler;
import com.puzzlebazar.server.handler.DoLogoutHandler;
import com.puzzlebazar.server.handler.GetUserInfoHandler;
import com.puzzlebazar.shared.action.DoLogin;
import com.puzzlebazar.shared.action.DoLogout;
import com.puzzlebazar.shared.action.GetUserInfo;

/**
 * Module which binds the handlers and configurations
 *
 */
public class ServerModule extends ActionHandlerModule {

  @Override
  protected void configureHandlers() {
    bindHandler(DoLogin.class, DoLoginHandler.class);
    bindHandler(DoLogout.class, DoLogoutHandler.class);
    bindHandler(GetUserInfo.class, GetUserInfoHandler.class);
  }
}
