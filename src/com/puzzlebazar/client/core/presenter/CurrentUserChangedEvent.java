package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.puzzlebazar.shared.model.User;

/**
 * This event is sent to the {@link EventBus} when the current user
 * information has changed. For example, if the user logged in or
 * logged out.
 *
 * @author Philippe Beaudoin
 */
public class CurrentUserChangedEvent extends GwtEvent<CurrentUserChangedHandler> {

    private static final Type<CurrentUserChangedHandler> TYPE = new Type<CurrentUserChangedHandler>();

    public static Type<CurrentUserChangedHandler> getType() {
        return TYPE;
    }

    public static void fire( EventBus eventBus, User userInfo ) {
        eventBus.fireEvent( new CurrentUserChangedEvent( userInfo ) );
    }

    private final User currentUser;

    public CurrentUserChangedEvent( User currentUser ) {
        this.currentUser = currentUser;
    }

    /**
     * Access the current user attached to this event.
     * 
     * @return The {@link User} object of the current user, 
     *         or <code>null</code> if user is not logged in.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    protected void dispatch( CurrentUserChangedHandler handler ) {
        handler.onCurrentUserChanged( this );
    }

    @Override
    public Type<CurrentUserChangedHandler> getAssociatedType() {
        return getType();
    }
}
