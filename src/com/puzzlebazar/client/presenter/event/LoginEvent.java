package com.puzzlebazar.client.presenter.event;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.puzzlebazar.shared.model.LoginInfo;

/**
 * Presenters can send this event to the {@link EventBus} when the user has successfully logged in.
 *
 * @author Philippe Beaudoin
 */
public class LoginEvent extends GwtEvent<LoginHandler> {

    private static final Type<LoginHandler> TYPE = new Type<LoginHandler>();

    public static Type<LoginHandler> getType() {
        return TYPE;
    }

    public static void fire( EventBus eventBus, LoginInfo loginInfo ) {
        eventBus.fireEvent( new LoginEvent( loginInfo ) );
    }

    private final LoginInfo loginInfo;

    public LoginEvent( LoginInfo loginInfo ) {
        this.loginInfo = loginInfo;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    @Override
    protected void dispatch( LoginHandler handler ) {
        handler.onLogin( this );
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<LoginHandler> getAssociatedType() {
        return getType();
    }
}
