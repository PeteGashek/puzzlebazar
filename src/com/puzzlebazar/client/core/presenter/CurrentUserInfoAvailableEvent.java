package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.puzzlebazar.shared.model.UserInfo;

/**
 * This event is sent to the {@link EventBus} when the login/logout URLs for the Google User Service
 * are available.
 *
 * @author Philippe Beaudoin
 */
public class CurrentUserInfoAvailableEvent extends GwtEvent<CurrentUserInfoAvailableHandler> {

    private static final Type<CurrentUserInfoAvailableHandler> TYPE = new Type<CurrentUserInfoAvailableHandler>();

    public static Type<CurrentUserInfoAvailableHandler> getType() {
        return TYPE;
    }

    public static void fire( EventBus eventBus, UserInfo userInfo ) {
        eventBus.fireEvent( new CurrentUserInfoAvailableEvent( userInfo ) );
    }

    private final UserInfo userInfo;

    public CurrentUserInfoAvailableEvent( UserInfo userInfo ) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    protected void dispatch( CurrentUserInfoAvailableHandler handler ) {
        handler.onCurrentUserInfoAvailable( this );
    }

    @Override
    public Type<CurrentUserInfoAvailableHandler> getAssociatedType() {
        return getType();
    }
}
