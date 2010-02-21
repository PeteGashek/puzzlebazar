package com.puzzlebazar.client.presenter.event;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.gwt.presenter.client.EventBus;

/**
 * This event is sent to the {@link EventBus} when the login/logout URLs for the Google User Service
 * are available.
 *
 * @author Philippe Beaudoin
 */
public class LoginURLsAvailableEvent extends GwtEvent<LoginURLsAvailableHandler> {

    private static final Type<LoginURLsAvailableHandler> TYPE = new Type<LoginURLsAvailableHandler>();

    public static Type<LoginURLsAvailableHandler> getType() {
        return TYPE;
    }

    public static void fire( EventBus eventBus, String loginURL, String logoutURL ) {
        eventBus.fireEvent( new LoginURLsAvailableEvent( loginURL, logoutURL ) );
    }

    private final String loginURL;
    private final String logoutURL;

    public LoginURLsAvailableEvent( String loginURL, String logoutURL ) {
      this.loginURL = loginURL;
      this.logoutURL = logoutURL;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public String getLogoutURL() {
      return logoutURL;
  }

    @Override
    protected void dispatch( LoginURLsAvailableHandler handler ) {
        handler.onLoginURLsAvailable( this );
    }

    @Override
    public Type<LoginURLsAvailableHandler> getAssociatedType() {
        return getType();
    }
}
