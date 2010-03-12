package com.philbeaudoin.platform.dispatch.client.gin;

import com.philbeaudoin.platform.dispatch.client.appengine.AppEngineSecureSessionAccessor;
import com.philbeaudoin.platform.dispatch.client.secure.SecureSessionAccessor;

import com.google.gwt.inject.client.AbstractGinModule;

/**
 * Configures the application to use Google App Engine security for
 * authentication.
 * 
 * @author David Peterson
 */
public class AppEngineSecurityModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind( SecureSessionAccessor.class ).to( AppEngineSecureSessionAccessor.class );
    }
}
