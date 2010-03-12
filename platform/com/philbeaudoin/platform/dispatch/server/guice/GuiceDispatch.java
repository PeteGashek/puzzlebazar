package com.philbeaudoin.platform.dispatch.server.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.philbeaudoin.platform.dispatch.server.AbstractDispatch;
import com.philbeaudoin.platform.dispatch.server.ActionHandlerRegistry;

@Singleton
public class GuiceDispatch extends AbstractDispatch {

    private final ActionHandlerRegistry handlerRegistry;

    @Inject
    public GuiceDispatch( ActionHandlerRegistry handlerRegistry ) {
        this.handlerRegistry = handlerRegistry;
    }

    @Override
    protected ActionHandlerRegistry getHandlerRegistry() {
        return handlerRegistry;
    }

}
