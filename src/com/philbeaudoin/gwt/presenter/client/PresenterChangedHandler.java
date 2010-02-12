package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.EventHandler;

public interface PresenterChangedHandler extends EventHandler {

    void onPresenterChanged( PresenterChangedEvent event );

}
