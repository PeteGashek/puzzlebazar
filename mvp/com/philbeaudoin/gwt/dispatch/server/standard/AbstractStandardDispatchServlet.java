package com.philbeaudoin.gwt.dispatch.server.standard;

import com.philbeaudoin.gwt.dispatch.client.standard.StandardDispatchService;
import com.philbeaudoin.gwt.dispatch.server.Dispatch;
import com.philbeaudoin.gwt.dispatch.shared.Action;
import com.philbeaudoin.gwt.dispatch.shared.ActionException;
import com.philbeaudoin.gwt.dispatch.shared.Result;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.philbeaudoin.gwt.dispatch.shared.ServiceException;

public abstract class AbstractStandardDispatchServlet extends RemoteServiceServlet implements StandardDispatchService {

  /**
   * 
   */
  private static final long serialVersionUID = -8306745239523629935L;

  public Result execute( Action<?> action ) throws ActionException, ServiceException {
    try {
      return getDispatch().execute( action );
    } catch ( RuntimeException e ) {
      log( "Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e );
      throw new ServiceException( e );
    }
  }

  /**
   * 
   * @return The Dispatch instance.
   */
  protected abstract Dispatch getDispatch();

}
