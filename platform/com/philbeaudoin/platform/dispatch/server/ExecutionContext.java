package com.philbeaudoin.platform.dispatch.server;

import com.philbeaudoin.platform.dispatch.shared.Action;
import com.philbeaudoin.platform.dispatch.shared.ActionException;
import com.philbeaudoin.platform.dispatch.shared.Result;
import com.philbeaudoin.platform.dispatch.shared.ServiceException;

/**
 * ExecutionContext instances are passed to {@link ActionHandler}s, and allows
 * them to execute sub-actions. These actions can be automatically rolled back
 * if any part of the action handler fails.
 * 
 * @author David Peterson
 */
public interface ExecutionContext {
    /**
     * Executes an action in the current context. If
     * <code>rollbackOnException</code> is set to <code>true</code>, the
     * action will be rolled back if the surrounding execution fails.
     * 
     * @param <A>
     *            The action type.
     * @param <R>
     *            The result type.
     * 
     * @param action
     *            The action.
     * @param allowRollback
     *            If <code>true</code>, any failure in the surrounding
     *            execution will trigger a rollback of the action.
     * @return The result.
     * @throws ActionException
     * @throws ServiceException 
     */
    <A extends Action<R>, R extends Result> R execute( A action, boolean allowRollback ) throws ActionException, ServiceException;

    /**
     * Executes an action in the current context. If the surrounding execution
     * fails, the action will be rolled back.
     * 
     * @param <A>
     *            The action type.
     * @param <R>
     *            The result type.
     * @param action
     *            The action.
     * @return The result.
     * @throws ActionException
     * @throws ServiceException 
     */
    <A extends Action<R>, R extends Result> R execute( A action ) throws ActionException, ServiceException;
}
