package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.HandlerContainerImpl;
import com.philbeaudoin.gwt.presenter.client.Presenter;

public class ProxyImpl<P extends Presenter> extends HandlerContainerImpl 
implements Proxy<P> {

  protected final EventBus eventBus;
  protected final CallbackProvider<P> presenter;

  /**
   * Creates a Proxy class for a specific presenter.
   * 
   * @param eventBus The {@link EventBus}.
   * @param presenter A {@link Provider} for the {@link Presenter} of which this class is a proxy. 
   */
  public ProxyImpl( final EventBus eventBus, final CallbackProvider<P> presenter ) {
    this.eventBus = eventBus;
    this.presenter = presenter;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void getAbstractPresenter(Callback<Presenter> callback) {
    presenter.get((Callback<P>) callback);
  }
  
  @Override
  public void getPresenter( Callback<P> callback ) {
    presenter.get(callback);
  }

  /**
   * By default, proxys can't reveal their presenter. Only leaf-level
   * proxies, such as {@link ProxyPlaceAbstract} can. 
   */
  @Override
  public void reveal() {
    assert false : "Unrevealable proxy.";
  }
  
  @Override
  public void onPresenterChanged( Presenter presenter ) {
  }

  @Override
  public void onPresenterRevealed( Presenter presenter ) {
  }


}
