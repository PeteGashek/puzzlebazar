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
 * <p/>
 * However, there may be more than one instance of concrete Place classes, so
 * the state should be shared between all instances of any given class. Usually
 * this is done via a shared class, such as a {@link Presenter} instance.
 *
 * @author David Peterson
 */
public abstract class ProxyPlaceImpl<P extends Presenter> extends PresenterProxyImpl<P> 
implements ProxyPlace {

  protected final PlaceManager placeManager;

  /**
   * Creates a {@link PresenterProxy} for a {@link Presenter} that 
   * is attached to a {@link Place}. That is, this presenter can
   * be invoked by setting its specific history token in the URL bar.
   * 
   * @param eventBus The {@link EventBus}.
   * @param placeManager The {@link PlaceManager}.
   * @param presenter A {@link Provider} for the {@link Presenter} of which this class is a proxy. 
   */
  public ProxyPlaceImpl( final EventBus eventBus, 
      final PlaceManager placeManager, 
      final Provider<P> presenter ) {
    super( eventBus, presenter );
    this.placeManager = placeManager;
  }

  @Override
  public final boolean equals( Object o ) {
    if ( o instanceof Place ) {
      Place place = (Place) o;
      return getHistoryToken().equals( place.getHistoryToken() );
    }
    return false;
  }

  @Override
  public final int hashCode() {
    return 17 * getHistoryToken().hashCode();
  }

  @Override
  public final String toString() {
    return getHistoryToken();
  }

  /**
   * Prepares the presenter with the information contained in the current request, then reveals the display.
   *
   * @param request The request to handle.
   */
  private final void handleRequest( PlaceRequest request ) {
    if( !placeManager.confirmLeaveState() )
      return;    
    P presenter = getPresenter();
    presenter.prepareFromRequest( request );
    presenter.reveal();
  }

  /**
   * Returns the request with any extra values relevant to the current place
   * initialised. If nothing needs to be done, return the request that was
   * passed in.
   * <p/>
   * <p/>
   * Typical usage:
   * <p/>
   * <pre>
   * return request.with( &quot;name&quot;, idValue ).with( &quot;name&quot;, &quot;Foo&quot; );
   * </pre>
   *
   * @param request The current request object.
   * @return The prepared request.
   */
  private final PlaceRequest prepareRequest( PlaceRequest request ) {
    return getPresenter().prepareRequest( request );
  }

  @Override
  public final boolean matchesRequest( PlaceRequest request ) {
    return getHistoryToken().equals( request.getName() );
  }

  @Override
  public final PlaceRequest createRequest() {
    return prepareRequest( new PlaceRequest( getHistoryToken() ) );
  }

  @Override
  public void onBind() {
    super.onBind();

    registerHandler( eventBus.addHandler( PlaceRequestEvent.getType(), new PlaceRequestHandler() {
      public void onPlaceRequest( PlaceRequestEvent event ) {
        PlaceRequest request = event.getRequest();
        if ( matchesRequest( request ) ) {
          handleRequest( request );
        }
      }
    } ) );
  }

  @Override
  public void onPresenterChanged() {
    super.onPresenterChanged();
    
    placeManager.onPlaceChanged( this );  
  }

  @Override
  public void onPresenterRevealed() {
    super.onPresenterRevealed();
    
    placeManager.onPlaceRevealed( this );  
  }

  @Override
  public final void reveal() {
    handleRequest( createRequest() );
  }
}
