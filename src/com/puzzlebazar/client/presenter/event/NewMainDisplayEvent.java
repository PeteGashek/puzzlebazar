package com.puzzlebazar.client.presenter.event;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;

/**
 * Presenters can send this event to the {@link EventBus} to become the main view.
 *
 * @author Philippe Beaudoin
 */
public class NewMainDisplayEvent extends GwtEvent<NewMainDisplayHandler> {

    private static final Type<NewMainDisplayHandler> TYPE = new Type<NewMainDisplayHandler>();

    public static Type<NewMainDisplayHandler> getType() {
        return TYPE;
    }

    public static void fire( EventBus eventBus, Presenter presenter ) {
        eventBus.fireEvent( new NewMainDisplayEvent( presenter ) );
    }

    private final Presenter presenter;

    public NewMainDisplayEvent( Presenter presenter ) {
        this.presenter = presenter;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void dispatch( NewMainDisplayHandler handler ) {
        handler.onNewMainDisplay( this );
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<NewMainDisplayHandler> getAssociatedType() {
        return getType();
    }
}
