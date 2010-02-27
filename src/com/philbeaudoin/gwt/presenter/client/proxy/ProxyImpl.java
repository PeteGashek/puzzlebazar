package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.HandlerContainer;
import com.philbeaudoin.gwt.presenter.client.Presenter;

public class ProxyImpl<P extends Presenter> extends HandlerContainer implements PresenterProxy {

  protected final EventBus eventBus;
  private final Provider<P> presenter;

  /**
   * Creates a BasicProxy class for a specific presenter.
   * 
   * @param eventBus The event bus.
   * @param presenter A provider for the {@link Presenter} of which this class is a proxy. 
   */
  public ProxyImpl( final EventBus eventBus, final Provider<P> presenter ) {
    this.eventBus = eventBus;
    this.presenter = presenter;
  }
  
  @Override
  public P getPresenter() {
    return presenter.get();
  }
  
  @Override
  public void onPresenterChanged() {
  }

  @Override
  public void onPresenterRevealed() {
  }

}
