package com.philbeaudoin.gwt.presenter.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.PresenterWrapper;

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
  protected <P extends Presenter, D extends PresenterDisplay, W extends PresenterWrapper<P>> void bindPresenter( 
      Class<P> presenter, 
      Class<D> display,
      Class<? extends D> displayImpl,
      Class<W> wrapper ) {
    bind( presenter ).in( Singleton.class );
    bindDisplay( display, displayImpl );
    bind( wrapper ).asEagerSingleton();
      
  }

  /**
   * Convenience method for binding a display implementation.
   *
   * @param <D>         The display interface type
   * @param display     The display interface
   * @param displayImpl The display implementation
   */
  protected <D extends PresenterDisplay> void bindDisplay( 
      Class<D> display, 
      Class<? extends D> displayImpl ) {
    bind( display ).to( displayImpl );
  }


}