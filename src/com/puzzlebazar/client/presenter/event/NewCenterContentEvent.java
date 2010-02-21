package com.puzzlebazar.client.presenter.event;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;

/**
 * Presenters can send this event to the {@link EventBus} to become the main view.
 *
 * @author Philippe Beaudoin
 */
public class NewCenterContentEvent extends GwtEvent<NewCenterContentHandler> {

    private static final Type<NewCenterContentHandler> TYPE = new Type<NewCenterContentHandler>();

    public static Type<NewCenterContentHandler> getType() {
        return TYPE;
    }

    public static void fire( EventBus eventBus, Presenter presenter ) {
        eventBus.fireEvent( new NewCenterContentEvent( presenter ) );
    }

    private final Presenter presenter;

    public NewCenterContentEvent( Presenter presenter ) {
        this.presenter = presenter;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void dispatch( NewCenterContentHandler handler ) {
        handler.onNewCenterContent( this );
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<NewCenterContentHandler> getAssociatedType() {
        return getType();
    }
}
