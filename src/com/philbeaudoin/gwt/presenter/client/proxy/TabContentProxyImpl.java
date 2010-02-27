package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.puzzlebazar.client.resources.Translations;
import com.puzzlebazar.client.ui.Tab;

public abstract class TabContentProxyImpl<P extends Presenter> 
extends ProxyPlaceImpl<P> implements TabContentProxy {

  /**
   * Subclasses will likely need translations for the text on the tab, so we provide the field 
   * in the parent class.
   */
  protected final Translations translations;
  private Tab tab = null;

  public TabContentProxyImpl(final EventBus eventBus, 
      final PlaceManager placeManager, 
      final Provider<P> presenter, 
      final Translations translations ) {
    super(eventBus, placeManager, presenter);
    
    this.translations = translations;
  }

  /**
   * Makes it possible to access the text associated with a tab presenter place.
   * This is the text that will typically be written on the tab.
   * 
   * @return The text.
   */
  public abstract String getText();

  @Override
  public void setTab(Tab tab) {
    this.tab  = tab;
  }

  @Override
  public Tab getTab() {
    return tab;
  }
  
  
}
