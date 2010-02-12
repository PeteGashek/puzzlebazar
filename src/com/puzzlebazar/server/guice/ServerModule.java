package com.puzzlebazar.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.puzzlebazar.server.handler.SendGreetingHandler;

/**
 * Module which binds the handlers and configurations
 *
 */
public class ServerModule extends ActionHandlerModule {

 @Override
 protected void configureHandlers() {
  bindHandler(SendGreetingHandler.class);
 }
}
