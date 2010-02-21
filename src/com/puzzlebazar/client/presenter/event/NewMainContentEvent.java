package com.puzzlebazar.client.presenter.event;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;

/**
 * Presenters can send this event to the {@link EventBus} to become the main view.
 *
 * @author Philippe Beaudoin
 */
public class NewMainContentEvent extends GwtEvent<NewMainContentHandler> {

    private static final Type<NewMainContentHandler> TYPE = new Type<NewMainContentHandler>();

    public static Type<NewMainContentHandler> getType() {
        return TYPE;
    }

    public static void fire( EventBus eventBus, Presenter presenter ) {
        eventBus.fireEvent( new NewMainContentEvent( presenter ) );
    }

    private final Presenter presenter;

    public NewMainContentEvent( Presenter presenter ) {
        this.presenter = presenter;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void dispatch( NewMainContentHandler handler ) {
        handler.onNewMainContent( this );
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<NewMainContentHandler> getAssociatedType() {
        return getType();
    }
}
