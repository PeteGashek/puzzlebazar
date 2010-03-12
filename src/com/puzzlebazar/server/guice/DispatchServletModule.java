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

  // TODO philippe.beaudoin@gmail.com
  // Uncomment when http://code.google.com/p/puzzlebazar/issues/detail?id=27 is unblocked.
  //  private static final Map<String, String> crawlFilterParams;
  //  static {
  //    Map<String, String> aMap = new HashMap<String, String>();
  //    aMap.put("defaultURI_0", "/puzzlebazar.html");
  //    aMap.put("redirectURI_0", "/PuzzlebazarHidden.html");
  //    aMap.put("defaultURI_1", "/puzzlebazardebug.html");
  //    aMap.put("redirectURI_1", "/PuzzlebazarDebugHidden.html");
  //    crawlFilterParams = Collections.unmodifiableMap(aMap);
  //  }    

  @Override
  public void configureServlets() {

    // TODO philippe.beaudoin@gmail.com
    // Uncomment when http://code.google.com/p/puzzlebazar/issues/detail?id=27 is unblocked.
    //    filter("/*").through(CrawlFilter.class, crawlFilterParams);
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
