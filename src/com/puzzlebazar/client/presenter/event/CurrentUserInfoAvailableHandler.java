package com.puzzlebazar.client.presenter.event;

import com.google.gwt.event.shared.EventHandler;

public interface CurrentUserInfoAvailableHandler extends EventHandler {

    void onCurrentUserInfoAvailable( CurrentUserInfoAvailableEvent event );

}
