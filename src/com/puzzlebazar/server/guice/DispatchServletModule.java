package com.puzzlebazar.server.guice;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.philbeaudoin.platform.dispatch.server.guice.GuiceStandardDispatchServlet;

public class DispatchServletModule extends ServletModule {

  @Override
  public void configureServlets() {
    serve("/puzzlebazar/dispatch").with(GuiceStandardDispatchServlet.class);
  }

  @Provides
  UserService getUserService() {
    return UserServiceFactory.getUserService();
  }

  @Provides @Singleton
  PersistenceManagerFactory getPersistenceManagerFactory() {
    return JDOHelper.getPersistenceManagerFactory("transactions-optional");
  }
  
}
