package com.philbeaudoin.platform.dispatch.client.gin;

import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.philbeaudoin.platform.dispatch.client.DefaultExceptionHandler;
import com.philbeaudoin.platform.dispatch.client.DispatchAsync;
import com.philbeaudoin.platform.dispatch.client.ExceptionHandler;
import com.philbeaudoin.platform.dispatch.client.appengine.AppEngineSecureSessionAccessor;
import com.philbeaudoin.platform.dispatch.client.secure.CookieSecureSessionAccessor;
import com.philbeaudoin.platform.dispatch.client.secure.SecureDispatchAsync;
import com.philbeaudoin.platform.dispatch.client.secure.SecureSessionAccessor;

/**
 * This module binds the {@link DispatchAsync} to {@link SecureDispatchAsync}.
 * For simple cases, just set this as a \@GinModule in your {@link Ginjector} instance.
 * <p/>
 * If you want to provide a custom {@link ExceptionHandler} just call
 * <code>install( new StandardDispatchModule( MyExceptionHandler.class )</code>
 * in another module.
 * <p/>
 * You must also provide another module which binds an implementation of
 * {@link SecureSessionAccessor}, such as {@link CookieSecureSessionAccessor}
 * or {@link AppEngineSecureSessionAccessor}.
 *
 * @author David Peterson
 */
public class SecureDispatchModule extends AbstractDispatchModule {

    /**
     * Constructs a new GIN configuration module that sets up a secure {@link com.philbeaudoin.platform.dispatch.client.DispatchAsync}
     * implementation, using the {@link com.philbeaudoin.platform.dispatch.client.DefaultExceptionHandler}.
     */
    public SecureDispatchModule() {
        this( DefaultExceptionHandler.class );
    }

    /**
     * Constructs a new GIN configuration module that sets up a secure {@link com.philbeaudoin.platform.dispatch.client.DispatchAsync}
     * implementation, using the provided {@link ExceptionHandler} implementation class.
     *
     * @param exceptionHandlerType The {@link ExceptionHandler} implementation class.
     */
    public SecureDispatchModule( Class<? extends ExceptionHandler> exceptionHandlerType ) {
        super( exceptionHandlerType );
    }

    @Provides
    @Singleton
    protected DispatchAsync provideDispatchAsync( ExceptionHandler exceptionHandler, SecureSessionAccessor secureSessionAccessor ) {
        return new SecureDispatchAsync( exceptionHandler, secureSessionAccessor );
    }

}
