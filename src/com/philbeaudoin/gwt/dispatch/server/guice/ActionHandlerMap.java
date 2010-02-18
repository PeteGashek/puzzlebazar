package com.philbeaudoin.gwt.dispatch.server.guice;

import com.philbeaudoin.gwt.dispatch.server.ActionHandler;
import com.philbeaudoin.gwt.dispatch.shared.Action;
import com.philbeaudoin.gwt.dispatch.shared.Result;

public interface ActionHandlerMap<A extends Action<R>, R extends Result> {
    public Class<A> getActionClass();
    
    public Class<? extends ActionHandler<A, R>> getActionHandlerClass();
}
