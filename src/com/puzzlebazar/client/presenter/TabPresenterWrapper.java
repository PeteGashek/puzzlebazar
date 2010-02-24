package com.puzzlebazar.client.presenter;

import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.BasicPresenterWrapper;
import com.philbeaudoin.gwt.presenter.client.place.Place;
import com.puzzlebazar.client.resources.Translations;
import com.puzzlebazar.client.ui.Tab;

public abstract class TabPresenterWrapper<P extends Presenter, Pl extends Place> 
extends BasicPresenterWrapper<P> {

  /**
   * Subclasses will likely need translations for the text on the tab, so we provide the field 
   * in the parent class.
   */
  private final Provider<Pl> place;
  protected final Translations translations;
  private Tab tab = null;

  public TabPresenterWrapper(EventBus eventBus, Provider<P> presenter, Provider<Pl> place, Translations translations ) {
    super(eventBus, presenter);
    
    this.place = place;
    this.translations = translations;
  }

  /**
   * Makes it possible to access the text associated with a tab presenter place.
   * This is the text that will typically be written on the tab.
   * 
   * @return The text.
   */
  public abstract String getText();
  
  public String getHistoryToken() {
    return place.get().getHistoryToken();
  }

  public void setTab(Tab tab) {
    this.tab  = tab;
  }

  public Tab getTab() {
    return tab;
  }

}
