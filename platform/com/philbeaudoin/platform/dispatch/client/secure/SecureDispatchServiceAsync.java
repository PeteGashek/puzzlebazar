package com.philbeaudoin.platform.dispatch.client.secure;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.philbeaudoin.platform.dispatch.shared.Action;
import com.philbeaudoin.platform.dispatch.shared.Result;

public interface SecureDispatchServiceAsync {
    void execute( String sessionId, Action<?> action, AsyncCallback<Result> callback );
}
