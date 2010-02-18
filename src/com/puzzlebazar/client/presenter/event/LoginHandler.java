package com.puzzlebazar.client.presenter.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoginHandler extends EventHandler {

    void onLogin( LoginEvent event );

}
