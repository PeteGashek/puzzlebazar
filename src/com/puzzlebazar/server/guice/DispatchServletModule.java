package com.puzzlebazar.server.guice;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.philbeaudoin.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;
import com.puzzlebazar.server.datastore.PMF;

public class DispatchServletModule extends ServletModule {

  @Override
  public void configureServlets() {
    serve("/puzzlebazar/dispatch").with(GuiceStandardDispatchServlet.class);
  }

  @Provides
  UserService provideUserService() {
    return UserServiceFactory.getUserService();
  }
  
  @Provides @RequestScoped
  PersistenceManager providePersistenceManager() {
    return PMF.get().getPersistenceManager();
  }
  
}
