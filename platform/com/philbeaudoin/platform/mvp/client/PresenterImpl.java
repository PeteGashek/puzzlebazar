package com.philbeaudoin.platform.mvp.client;

import com.philbeaudoin.platform.mvp.client.proxy.PlaceRequest;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.philbeaudoin.platform.mvp.client.proxy.SetContentEvent;

public abstract class PresenterImpl<V extends View, Proxy_ extends Proxy<?>> 
extends PresenterWidgetImpl<V> implements Presenter {

  /**
   * The light-weight {@PresenterProxy} around this presenter.
   */
  protected final Proxy_ proxy;
  
  /**
   * Creates a basic {@link Presenter}.
   * @param eventBus The event bus.
   * @param view  The view attached to this presenter.
   * @param proxy The presenter proxy.
   */
  public PresenterImpl( 
      final EventBus eventBus, 
      final V view, 
      final Proxy_ proxy ) {
    super(eventBus, view);
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
