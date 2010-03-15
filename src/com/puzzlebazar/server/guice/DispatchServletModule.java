package com.puzzlebazar.server.guice;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.dyuproject.openid.OpenIdServletFilter;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.philbeaudoin.platform.dispatch.server.guice.GuiceStandardDispatchServlet;
import com.puzzlebazar.server.currentuser.OpenIdServlet;


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


  private static final Map<String, String> openIdFilterParams;
  static {
    Map<String, String> aMap = new HashMap<String, String>();
    aMap.put("forwardUri", "/WEB-INF/views/jsp/login.jsp");
    openIdFilterParams = Collections.unmodifiableMap(aMap);
  }

  // Singletons
  private PersistenceManagerFactory persistenceManagerFactory;
  private Cache cache;

  @Override
  public void configureServlets() {

    // Instanciate singletons
    persistenceManagerFactory  = JDOHelper.getPersistenceManagerFactory("transactions-optional");
    cache = null;
    try {
      CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
      cache = cacheFactory.createCache(Collections.emptyMap());
    } catch (CacheException cause) {
      cause.printStackTrace();
    }

    // TODO philippe.beaudoin@gmail.com
    // Uncomment when http://code.google.com/p/puzzlebazar/issues/detail?id=27 is unblocked.
    //    filter("/*").through(CrawlFilter.class, crawlFilterParams);
    bind(OpenIdServletFilter.class).in(Singleton.class);

    filter("/user/*").through(OpenIdServletFilter.class, openIdFilterParams);
    serve("/puzzlebazar/dispatch").with(GuiceStandardDispatchServlet.class);
    serve("/user/home/").with(OpenIdServlet.class);
  }

  @Provides
  PersistenceManagerFactory getPersistenceManagerFactory() {
    return persistenceManagerFactory;
  }

  @Provides
  Cache getCache() {
    return cache;
  }

}
