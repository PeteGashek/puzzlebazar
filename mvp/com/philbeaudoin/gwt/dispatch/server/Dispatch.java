package com.philbeaudoin.gwt.dispatch.server;

import com.philbeaudoin.gwt.dispatch.shared.Action;
import com.philbeaudoin.gwt.dispatch.shared.ActionException;
import com.philbeaudoin.gwt.dispatch.shared.Result;
import com.philbeaudoin.gwt.dispatch.shared.ServiceException;

public interface Dispatch {

    /**
     * Executes the specified action and returns the appropriate result.
     * 
     * @param <R>
     *            The {@link Result} type.
     * @param action
     *            The {@link Action}.
     * @return The action's result.
     * @throws ActionException
     *             if the action execution failed.
     * @throws ServiceException 
     *             if the execution failed due to a service error.
     */
    <A extends Action<R>, R extends Result> R execute( A action ) throws ActionException, ServiceException;
}
