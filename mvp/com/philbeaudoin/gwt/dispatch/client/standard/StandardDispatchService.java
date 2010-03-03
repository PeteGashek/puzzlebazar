package com.philbeaudoin.gwt.dispatch.client.standard;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.philbeaudoin.gwt.dispatch.client.DispatchAsync;
import com.philbeaudoin.gwt.dispatch.server.Dispatch;
import com.philbeaudoin.gwt.dispatch.shared.Action;
import com.philbeaudoin.gwt.dispatch.shared.ActionException;
import com.philbeaudoin.gwt.dispatch.shared.Result;
import com.philbeaudoin.gwt.dispatch.shared.ServiceException;

/**
 * There currently seem to be GWT compilation problems with services that use
 * generic templates in method parameters. As such, they are stripped here, but
 * added back into the {@link Dispatch} and {@link DispatchAsync}, which are
 * the interfaces that should be accessed by regular code.
 * <p/>
 * Once GWT can compile templatized methods correctly, this should be merged
 * into a single pair of interfaces.
 *
 * @author David Peterson
 */
@RemoteServiceRelativePath("dispatch")
public interface StandardDispatchService extends RemoteService {
    Result execute( Action<?> action ) throws ActionException, ServiceException;
}
