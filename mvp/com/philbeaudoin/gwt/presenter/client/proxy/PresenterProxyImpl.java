package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.HandlerContainer;
import com.philbeaudoin.gwt.presenter.client.Presenter;

public class PresenterProxyImpl<P extends Presenter> extends HandlerContainer implements Proxy {

  protected final EventBus eventBus;
  protected final CallbackProvider<P> presenter;

  /**
   * Creates a Proxy class for a specific presenter.
   * 
   * @param eventBus The {@link EventBus}.
   * @param presenter A {@link Provider} for the {@link Presenter} of which this class is a proxy. 
   */
  public PresenterProxyImpl( final EventBus eventBus, final CallbackProvider<P> presenter ) {
    this.eventBus = eventBus;
    this.presenter = presenter;
  }


  /**
   * Get the associated {@link Presenter}. The presenter can only be obtained in an asynchronous
   * manner, to support code splitting when needed. To access the presenter, pass a callback
   * 
   * @param callback
   */
  protected void getPresenter( Callback<P> callback ) {
    presenter.get(callback);
  }
  
  @Override
  public void onPresenterChanged( Presenter presenter ) {
  }

  @Override
  public void onPresenterRevealed( Presenter presenter ) {
  }

}
