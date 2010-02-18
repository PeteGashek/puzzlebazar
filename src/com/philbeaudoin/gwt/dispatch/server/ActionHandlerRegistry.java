package com.philbeaudoin.gwt.dispatch.server;

import com.philbeaudoin.gwt.dispatch.shared.Action;
import com.philbeaudoin.gwt.dispatch.shared.Result;

public interface ActionHandlerRegistry {

    /**
     * Searches the registry and returns the first handler which supports the
     * specied action, or <code>null</code> if none is available.
     * 
     * @param action
     *            The action.
     * @return The handler.
     */
    public <A extends Action<R>, R extends Result> ActionHandler<A, R> findHandler( A action );

    /**
     * Clears all registered handlers from the registry.
     */
    public void clearHandlers();
}
