package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceRequest;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;

public abstract class PresenterImpl<D extends Display, Proxy_ extends Proxy<?>> 
extends PresenterWidgetImpl<D> implements Presenter {

  /**
   * The light-weight {@PresenterProxy} around this presenter.
   */
  protected final Proxy_ proxy;
  
  /**
   * Creates a basic {@link Presenter}.
   * @param eventBus The event bus.
   * @param display  The display attached to this presenter.
   * @param proxy The presenter proxy.
   * @param setContentInParentEventType  The {@link Type} of the event this presenter will fire when it is
   *        revealed and needs to be set as content within its parent.
   *        Can be null if the presenter is top-level or if this
   *        presenter is inserted at bind-time and never modified 
   *        or revealed directly.
   */
  public PresenterImpl( 
      final EventBus eventBus, 
      final D display, 
      final Proxy_ proxy ) {
    super(eventBus, display);
    this.proxy = proxy;
  }
  
  @Override
  public final Proxy_ getProxy() {
    return proxy;
  }

  @Override
  public final void reveal() {
    onReveal();
    setContentInParent();
    getProxy().onPresenterRevealed( this );
  }

  /**
   * Called whenever the presenter needs to set its content in 
   * a parent. Should usually fire a {@link SetContentEvent}.
   */
  protected abstract void setContentInParent();

  @Override
  public final void notifyChange() {
    getProxy().onPresenterChanged( this );
  }
  
  @Override
  public void prepareFromRequest(PlaceRequest request) {
    // By default, no parameter to extract from request.
  }

  @Override
  public PlaceRequest prepareRequest(PlaceRequest request) {
    // By default, no parameter to add to request
    return request;
  }
  
}
