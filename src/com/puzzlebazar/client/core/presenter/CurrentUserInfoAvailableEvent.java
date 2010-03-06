package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.puzzlebazar.shared.model.User;

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

    public static void fire( EventBus eventBus, User userInfo ) {
        eventBus.fireEvent( new CurrentUserInfoAvailableEvent( userInfo ) );
    }

    private final User userInfo;

    public CurrentUserInfoAvailableEvent( User userInfo ) {
        this.userInfo = userInfo;
    }

    public User getUserInfo() {
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
