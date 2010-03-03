package com.philbeaudoin.gwt.dispatch.client.secure;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.philbeaudoin.gwt.dispatch.shared.Action;
import com.philbeaudoin.gwt.dispatch.shared.Result;

public interface SecureDispatchServiceAsync {
    void execute( String sessionId, Action<?> action, AsyncCallback<Result> callback );
}
