package com.philbeaudoin.gwt.presenter.client;

import com.google.inject.Provider;

/**
 * A slot is a specific location within a {@link PresenterContainer} into which a
 * sub-presenter can display itself.
 * 
 * @author beaudoin
 */
public abstract class Slot<P extends Presenter>  {

  protected Presenter activePresenter = null;
  private final Provider<P> presenter;
  
  protected Slot(Provider<P> presenter) {
    this.presenter = presenter;
  }
  
  public P getPresenter() {
    return presenter.get();
  }
  
  public void setPresenter(Presenter presenter) {
    if( activePresenter  != presenter ) {
      activePresenter = presenter;
      displayContent();
    }
  }
  
  protected abstract void displayContent();
  
}
