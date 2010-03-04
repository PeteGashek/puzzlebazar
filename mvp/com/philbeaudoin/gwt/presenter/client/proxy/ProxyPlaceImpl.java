package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;

/**
 * A proxy with a place represents a particular 'bookmark' or location inside the
 * application. A place is stateful - it may represent a location with it's
 * current settings, such as a particular ID value, or other unique indicators
 * that will allow a user to track back to that location later, either via a
 * browser bookmark, or by clicking the 'back' button.
 *
 * @author David Peterson
 * @author Philippe Beaudoin
 */
public abstract class ProxyPlaceImpl<P extends Presenter> extends PresenterProxyImpl<P> 
implements ProxyPlace {

  protected final PlaceManager placeManager;

  /**
   * Creates a {@link ProxyPlace} for a {@link Presenter} that 
   * is attached to a {@link Place}. That is, this presenter can
   * be invoked by setting its a history token that matches
   * its name token in the URL bar.
   * 
   * @param eventBus The {@link EventBus}.
   * @param placeManager The {@link PlaceManager}.
   * @param presenter A {@link Provider} for the {@link Presenter} of which this class is a proxy. 
   */
  public ProxyPlaceImpl( 
      final EventBus eventBus, 
      final PlaceManager placeManager, 
      final CallbackProvider<P> presenter ) {
    super( eventBus, presenter );
    this.placeManager = placeManager;
  }

  /**
   * Proxy places are equal if their name token matches.
   */
  @Override
  public final boolean equals( Object o ) {
    if ( o instanceof Place ) {
      Place place = (Place) o;
      return getNameToken().equals( place.getNameToken() );
    }
    return false;
  }

  @Override
  public final int hashCode() {
    return 17 * getNameToken().hashCode();
  }

  @Override
  public final String toString() {
    return getNameToken();
  }

  /**
   * Prepares the presenter with the information contained in the current 
   * request, then reveals it. Will refuse to reveal the display and do 
   * nothing if {@link canReveal()} returns <code>false</code>. 
   *
   * @param request The request to handle. Can pass <code>null</code> if
   *                no request is used, in which case the presenter will
   *                be directly revealed. 
   */
  private final void handleRequest( final PlaceRequest request ) {
    if( !canReveal() || !placeManager.confirmLeaveState() )
      return;
    presenter.get( new Callback<P>() {
      @Override public void execute(P presenter) {
        if( request != null )
          presenter.prepareFromRequest( request );
        presenter.reveal();
      }
    } );
    
  }

  @Override
  public final boolean matchesRequest( PlaceRequest request ) {
    return request.matchesNameToken( getNameToken() );
  }

  @Override
  public void onBind() {
    super.onBind();

    registerHandler( eventBus.addHandler( PlaceRequestEvent.getType(), new PlaceRequestHandler() {
      public void onPlaceRequest( PlaceRequestEvent event ) {
        if( event.isHandled() )
          return;
        PlaceRequest request = event.getRequest();
        if ( matchesRequest( request ) && canReveal() ) {
          event.setHandled();
          handleRequest( request );
        }
      }
    } ) );
  }

  @Override
  public void onPresenterChanged( Presenter presenter ) {
    super.onPresenterChanged( presenter );    
    placeManager.onPlaceChanged( presenter.prepareRequest( new PlaceRequest(getNameToken())) );  
  }

  @Override
  public void onPresenterRevealed( Presenter presenter ) {
    super.onPresenterRevealed( presenter );
    
    placeManager.onPlaceRevealed( presenter.prepareRequest( new PlaceRequest(getNameToken())) );  
  }

  @Override
  public final void reveal() {
    handleRequest(null);
  }
  
  @Override
  public boolean canReveal() {
    return true;
  }
}

