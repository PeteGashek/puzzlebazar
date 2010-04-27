/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.puzzlebazar.server.guice;

import java.util.Collections;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.dyuproject.openid.OpenIdServletFilter;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFactory;
import com.philbeaudoin.gwtp.dispatch.server.guice.GuiceStandardDispatchServlet;
import com.puzzlebazar.server.OpenIdServlet;
import com.puzzlebazar.server.model.UserDAO;


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
    
    // Model object managers
    bind(ObjectifyFactory.class).in(Singleton.class);
    bind(UserDAO.class).in(RequestScoped.class);
    

    // TODO philippe.beaudoin@gmail.com
    // Uncomment when http://code.google.com/p/puzzlebazar/issues/detail?id=27 is unblocked.
    //    filter("/*").through(CrawlFilter.class, crawlFilterParams);
    bind(OpenIdServletFilter.class).in(Singleton.class);

    serveRegex("/puzzlebazar[^/]*/dispatch").with(GuiceStandardDispatchServlet.class);
    serve("/openid/login").with(OpenIdServlet.class);
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
