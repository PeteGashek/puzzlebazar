package com.philbeaudoin.gwt.presenter.client.place;

import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.PresenterChangedEvent;
import com.philbeaudoin.gwt.presenter.client.PresenterChangedHandler;
import com.philbeaudoin.gwt.presenter.client.PresenterRevealedEvent;
import com.philbeaudoin.gwt.presenter.client.PresenterRevealedHandler;
import com.philbeaudoin.gwt.presenter.client.PresenterWrapper;

/**
 * This is a subclass of {@link Place} with some helper values for working with
 * {@link Presenter}s.
 *
 * @author David Peterson
 */
public abstract class PresenterPlace<W extends PresenterWrapper<P>, P extends Presenter> extends Place {

  private final PlaceManager placeManager;
  private final W presenterWrapper;

  public PresenterPlace( final EventBus eventBus, final PlaceManager placeManager, final W presenterWrapper ) {
    super(eventBus);
    this.placeManager = placeManager;
    this.presenterWrapper = presenterWrapper;
  }

  public final W getPresenterWrapper() { return presenterWrapper; }

  public final P getPresenter() { return getPresenterWrapper().getPresenter(); }

  /**
   * Prepares the presenter with the information contained in the current request, then reveals the display.
   */
  @Override
  protected void handleRequest( PlaceRequest request ) {
    P presenter = getPresenter();
    presenter.prepareFromRequest( request );
    presenter.revealDisplay();
  }

  @Override
  protected final PlaceRequest prepareRequest( PlaceRequest request ) {
    return getPresenter().prepareRequest( request );
  }

  @Override
  protected void onBind() {
    super.onBind();

    placeManager.registerPlace( this );

    registerHandler( eventBus.addHandler( getPresenterWrapper().getPresenterChangedEventType(), 
        new PresenterChangedHandler() {
      @Override
      public void onPresenterChanged( PresenterChangedEvent event ) {
        PlaceChangedEvent.fire( eventBus, PresenterPlace.this );
      } 
    } ) );

    registerHandler( eventBus.addHandler( getPresenterWrapper().getPresenterRevealedEventType(),  
        new PresenterRevealedHandler() {
      public void onPresenterRevealed( PresenterRevealedEvent event ) {
        PlaceRevealedEvent.fire( eventBus, PresenterPlace.this );
      } 
    } ) );

  }

  @Override
  protected void onUnbind() {
    placeManager.deregisterPlace( this );
  }
}
