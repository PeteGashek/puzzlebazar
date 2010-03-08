package com.philbeaudoin.gwt.presenter.client.proxy;

import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.HandlerContainerImpl;
import com.philbeaudoin.gwt.presenter.client.Presenter;

/**
 * A useful mixing class to define a {@link Proxy} that is also
 * a {@link Place}. You can usually use the simpler form {@link ProxyPlace}.
 * <p />
 * @param <P> Type of the associated {@link Presenter}.
 * @param <Proxy_> Type of the associated {@link Proxy}.
 *
 * @author David Peterson
 * @author Philippe Beaudoin
 */
public class ProxyPlaceAbstract<P extends Presenter, Proxy_ extends Proxy<P>> 
extends HandlerContainerImpl 
implements Proxy<P>, Place {

  protected final EventBus eventBus;
  protected final PlaceManager placeManager;
  protected final Proxy_ proxy;
  protected final Place place;

  /**
   * Creates a {@link ProxyPlace} for a {@link Presenter} that 
   * is attached to a {@link ProxyPlace}. That is, this presenter can
   * be invoked by setting its a history token that matches
   * its name token in the URL bar.
   * 
   * @param eventBus The {@link EventBus}.
   * @param placeManager The {@link PlaceManager}.
   * @param proxy The {@link Proxy} to wrap within a {@link ProxyPlace}. 
   */
  public ProxyPlaceAbstract( 
      final EventBus eventBus, 
      final PlaceManager placeManager, 
      final Proxy_ proxy,
      final Place place) {
    this.eventBus = eventBus;
    this.placeManager = placeManager;
    this.proxy = proxy;
    this.place = place;
  }
  
  ///////////////////////
  // Inherited from Proxy
  
  @Override
  protected void onBind() {
    super.onBind();
    proxy.bind();

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
  protected void onUnbind() {
    super.onUnbind();
    proxy.unbind();
  }

  @Override
  public final void reveal() {
    handleRequest(null);
  }

  @Override
  public void getAbstractPresenter(Callback<Presenter> callback) {
    proxy.getAbstractPresenter(callback);
  }
  
  @Override
  public void getPresenter(Callback<P> callback) {
    proxy.getPresenter(callback);
  }
  
  @Override
  public void onPresenterChanged( Presenter presenter ) {
    proxy.onPresenterChanged( presenter );
    placeManager.onPlaceChanged( presenter.prepareRequest( new PlaceRequest(getNameToken())) );  
  }

  @Override
  public void onPresenterRevealed( Presenter presenter ) {
    proxy.onPresenterRevealed( presenter );    
    placeManager.onPlaceRevealed( presenter.prepareRequest( new PlaceRequest(getNameToken())) );  
  }

  
  ///////////////////////
  // Inherited from Place
  
  @Override
  public final boolean equals( Object o ) {
    return place.equals(o);
  }

  @Override
  public final int hashCode() {
    return place.hashCode();
  }

  @Override
  public final String toString() {
    return place.toString();
  }

  @Override
  public String getNameToken() {
    return place.getNameToken();
  }

  @Override
  public boolean matchesRequest(PlaceRequest request) {
    return place.matchesRequest(request);
  }
  
  @Override
  public boolean canReveal() {
    return place.canReveal();
  }
  
  ///////////////////////
  // Private methods
  
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
    proxy.getPresenter( new Callback<P>() {
      @Override public void execute(P presenter) {
        if( request != null )
          presenter.prepareFromRequest( request );
        presenter.reveal();
      }
    } );
    
  }

}

