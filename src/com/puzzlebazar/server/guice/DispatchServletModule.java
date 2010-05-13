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

import com.dyuproject.openid.OpenIdServletFilter;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.SessionScoped;
import com.googlecode.objectify.ObjectifyFactory;
import com.philbeaudoin.gwtp.crawler.server.CrawlFilter;
import com.philbeaudoin.gwtp.crawler.server.HtmlUnitTimeout;
import com.philbeaudoin.gwtp.dispatch.server.DispatchServiceImpl;
import com.philbeaudoin.gwtp.dispatch.server.HttpSessionSecurityCookieFilter;
import com.philbeaudoin.gwtp.dispatch.shared.ActionImpl;
import com.philbeaudoin.gwtp.dispatch.shared.SecurityCookie;
import com.puzzlebazar.server.OpenIdServlet;
import com.puzzlebazar.shared.Constants;


public class DispatchServletModule extends ServletModule {


  @Override
  public void configureServlets() {

    
    // Model object managers
    bind(ObjectifyFactory.class).in(Singleton.class);
    
    bindConstant().annotatedWith( HtmlUnitTimeout.class ).to( 15000L );
    bindConstant().annotatedWith( SecurityCookie.class ).to(Constants.securityCookieName);

    // TODO philippe.beaudoin@gmail.com
    // Uncomment when http://code.google.com/p/puzzlebazar/issues/detail?id=27 is unblocked.
    bind(OpenIdServletFilter.class).in(Singleton.class);

    filter("*.jsp").through( CrawlFilter.class );
    filter("*.jsp").through( HttpSessionSecurityCookieFilter.class );
    serveRegex("/puzzlebazar[^/]*/" + ActionImpl.DEFAULT_SERVICE_NAME).with(DispatchServiceImpl.class);
    serve("/openid/login").with(OpenIdServlet.class);
    
  }

  @Provides
  @SessionScoped
  WebClient provideWebClient() {
    return new WebClient( BrowserVersion.FIREFOX_3 );
  }  
  
}
