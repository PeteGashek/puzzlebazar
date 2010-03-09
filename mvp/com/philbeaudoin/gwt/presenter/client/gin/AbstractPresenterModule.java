package com.philbeaudoin.gwt.presenter.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.PresenterWidget;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;

public abstract class AbstractPresenterModule extends AbstractGinModule {

  public AbstractPresenterModule() {
    super();
  }

  /**
   * Convenience method for binding a presenter as well as it's display.
   *
   * @param <P>         The {@link PresenterWidget} type.
   * @param <D>         The {@link Display} type.
   * @param presenter   The {@link PresenterWidget} (NOT a singleton).
   * @param display     The {@link Display} interface.
   * @param displayImpl The {@link Display} implementation (NOT a singleton).
   */
  protected <P extends PresenterWidget, D extends Display> void bindPresenterWidget( 
      Class<P> presenter, 
      Class<D> display,
      Class<? extends D> displayImpl ) {
    bind( presenter );
    bind( displayImpl );
    bind( display ).to( displayImpl );
  }
  
  /**
   * Convenience method for binding a presenter as well as it's display.
   *
   * @param <P>         The {@link Presenter} type.
   * @param <D>         The {@link Display} type.
   * @param <Proxy_>    The {@link Proxy} type.
   * @param presenter   The {@link Presenter} (a singleton).
   * @param display     The {@link Display} interface.
   * @param displayImpl The {@link Display} implementation (a singleton).
   * @param proxy       The {@link Proxy} interface.
   * @param proxyImpl   The {@link Proxy} implementation (a singleton).
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