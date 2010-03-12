package com.philbeaudoin.platform.dispatch.client.secure;

import com.philbeaudoin.platform.dispatch.shared.Action;
import com.philbeaudoin.platform.dispatch.shared.ActionException;
import com.philbeaudoin.platform.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.philbeaudoin.platform.dispatch.shared.ServiceException;

@RemoteServiceRelativePath("dispatch")
public interface SecureDispatchService extends RemoteService {
    Result execute( String sessionId, Action<?> action ) throws ActionException, ServiceException;
}
