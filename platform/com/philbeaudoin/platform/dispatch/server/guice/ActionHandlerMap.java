package com.philbeaudoin.platform.dispatch.server.guice;

import com.philbeaudoin.platform.dispatch.server.ActionHandler;
import com.philbeaudoin.platform.dispatch.shared.Action;
import com.philbeaudoin.platform.dispatch.shared.Result;

public interface ActionHandlerMap<A extends Action<R>, R extends Result> {
    public Class<A> getActionClass();
    
    public Class<? extends ActionHandler<A, R>> getActionHandlerClass();
}
