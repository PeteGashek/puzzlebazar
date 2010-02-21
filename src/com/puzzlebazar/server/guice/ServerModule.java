package com.puzzlebazar.server.guice;

import com.philbeaudoin.gwt.dispatch.server.guice.ActionHandlerModule;

import com.puzzlebazar.server.handler.GetLoginURLsHandler;
import com.puzzlebazar.server.handler.GetUserInfoHandler;
import com.puzzlebazar.shared.action.GetLoginURLs;
import com.puzzlebazar.shared.action.GetUserInfo;

/**
 * Module which binds the handlers and configurations
 *
 */
public class ServerModule extends ActionHandlerModule {

  @Override
  protected void configureHandlers() {
    bindHandler(GetLoginURLs.class, GetLoginURLsHandler.class);
    bindHandler(GetUserInfo.class, GetUserInfoHandler.class);
  }
}
