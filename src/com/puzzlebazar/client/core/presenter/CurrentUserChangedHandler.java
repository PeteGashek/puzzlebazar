package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.EventHandler;

public interface CurrentUserChangedHandler extends EventHandler {

    void onCurrentUserChanged( CurrentUserChangedEvent event );

}
