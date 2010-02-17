package com.philbeaudoin.gwt.presenter.client.place;

import com.google.gwt.event.shared.HandlerRegistration;
import com.philbeaudoin.gwt.presenter.client.*;

/**
 * This is a subclass of {@link Place} with some helper values for working with
 * {@link Presenter}s.
 *
 * @author David Peterson
 */
public abstract class PresenterPlace<T extends Presenter> extends Place {

    private HandlerRegistration presenterChangedRegistration;
    private HandlerRegistration presenterRevealedRegistration;

    public PresenterPlace() {
    }

    public abstract T getPresenter();

    /**
     * Calls the {@link Presenter#revealDisplay()} method for the place's
     * presenter.
     */
    @Override
    public final void reveal() {
        getPresenter().revealDisplay();
    }

    /**
     * Reveals the display. Subclasses should override this method to perform
     * any custom handling.
     */
    @Override
    protected final void handleRequest( PlaceRequest request ) {
        T presenter = getPresenter();
        presenter.prepareFromRequest( request );
        presenter.revealDisplay();
    }

    @Override
    protected final PlaceRequest prepareRequest( PlaceRequest request ) {
        return getPresenter().prepareRequest( request );
    }

    /**
     * Returns the {@link Presenter} for the provided {@link Place} if the place
     * is an instance of {@link PresenterPlace} and the contained
     * {@link Presenter} is an instance of the <code>presenterClass</code>.
     * If not, <code>null</code> is returned.
     *
     * @param place The place.
     * @return The {@link Presenter}, if appropriate.
     */
    public static Presenter getPresenter( Place place ) {
        if ( place instanceof PresenterPlace<?> ) {
            return ( (PresenterPlace<?>) place ).getPresenter();
        }
        return null;
    }

    @Override
    protected void addHandlers( final EventBus eventBus ) {
        super.addHandlers( eventBus );

        presenterChangedRegistration = eventBus.addHandler( PresenterChangedEvent.getType(), new PresenterChangedHandler() {
            /**
             * Listens for {@link com.philbeaudoin.gwt.presenter.client.PresenterChangedEvent}s that match the place's
             * {@link Presenter} and fires {@link PlaceChangedEvent} based on the
             * {@link Presenter}'s current state, calling
             * {@link PresenterPlace#prepareRequest(PlaceRequest, Presenter)} to configure the
             * request.
             *
             * @param event The event.
             */
            public void onPresenterChanged( PresenterChangedEvent event ) {
                if ( PresenterPlace.this.getPresenter() == event.getPresenter() )
                    PlaceChangedEvent.fire( eventBus, PresenterPlace.this );
            }
        } );

        presenterRevealedRegistration = eventBus.addHandler( PresenterRevealedEvent.getType(), new PresenterRevealedHandler() {
            public void onPresenterRevealed( PresenterRevealedEvent event ) {
                if ( PresenterPlace.this.getPresenter() == event.getPresenter() )
                    PlaceRevealedEvent.fire( eventBus, PresenterPlace.this );
            }
        } );

    }

    @Override
    protected void removeHandlers( EventBus eventBus ) {
        super.removeHandlers( eventBus );

        if ( presenterChangedRegistration != null ) {
            presenterChangedRegistration.removeHandler();
            presenterChangedRegistration = null;
        }

        if ( presenterRevealedRegistration != null ) {
            presenterRevealedRegistration.removeHandler();
            presenterRevealedRegistration = null;
        }
    }

}
