package com.philbeaudoin.gwt.dispatch.server.secure;

import com.philbeaudoin.gwt.dispatch.client.secure.SecureDispatchService;
import com.philbeaudoin.gwt.dispatch.server.Dispatch;
import com.philbeaudoin.gwt.dispatch.shared.Action;
import com.philbeaudoin.gwt.dispatch.shared.ActionException;
import com.philbeaudoin.gwt.dispatch.shared.Result;
import com.philbeaudoin.gwt.dispatch.shared.ServiceException;
import com.philbeaudoin.gwt.dispatch.shared.secure.InvalidSessionException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public abstract class AbstractSecureDispatchServlet extends RemoteServiceServlet implements SecureDispatchService {

  private static final long serialVersionUID = -1995842556570759707L;

  public Result execute( String sessionId, Action<?> action ) throws ActionException, ServiceException {
    try {
      if ( getSessionValidator().isValid( sessionId, getThreadLocalRequest() ) ) {
        return getDispatch().execute( action );
      } else {
        throw new InvalidSessionException();
      }
    } catch ( RuntimeException e ) {
      log( "Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e );
      throw new ServiceException( e.getMessage() );
    }
  }

  protected abstract SecureSessionValidator getSessionValidator();

  protected abstract Dispatch getDispatch();
}