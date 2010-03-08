package com.philbeaudoin.gwt.presenter.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;

public abstract class AbstractPresenterModule extends AbstractGinModule {

  public AbstractPresenterModule() {
    super();
  }

  /**
   * Convenience method for binding a presenter as well as it's display.
   *
   * @param <D>         The display type.
   * @param presenter   The presenter.
   * @param display     The display type.
   * @param displayImpl The display implementation.
   */
  protected <P extends Presenter, D extends Display, Proxy_ extends Proxy<P>> void bindPresenter( 
      Class<P> presenter, 
      Class<D> display,
      Class<? extends D> displayImpl,
      Class<Proxy_> proxy,
      Class<? extends Proxy_> proxyImpl ) {
    bind( presenter ).in( Singleton.class );
    bind( displayImpl ).in( Singleton.class );
    bind( proxyImpl ).asEagerSingleton();
    bind( display ).to( displayImpl );
    bind( proxy ).to( proxyImpl );
  }

}