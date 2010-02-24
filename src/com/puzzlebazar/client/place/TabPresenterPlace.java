package com.puzzlebazar.client.place;

import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.PresenterWrapper;
import com.philbeaudoin.gwt.presenter.client.place.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.place.PresenterPlace;
import com.puzzlebazar.client.presenter.TabbedPresenter;
import com.puzzlebazar.client.resources.Translations;

public abstract class TabPresenterPlace<W extends PresenterWrapper<P>, P extends Presenter> 
extends PresenterPlace<W, P> {

  /**
   * Subclasses will likely need translations for the text on the tab, so we provide the field 
   * in the parent class.
   */
  protected final Translations translations;

  /**
   * Creates a {@link PresenterPlace} that sits in a tab within a {@link TabbedPresenter}.
   * 
   * @param eventBus The event bus.
   * @param placeManager The place manager.
   * @param wrapper The wrapper of the associated {@link Presenter}.
   * @param translations The translation resources.
   */
  public TabPresenterPlace(EventBus eventBus, PlaceManager placeManager, W wrapper, Translations translations ) {
    super(eventBus, placeManager, wrapper);
    this.translations = translations;
  }

  /**
   * Makes it possible to access the text associated with a tab presenter place.
   * This is the text that will typically be written on the tab.
   * 
   * @return The text.
   */
  public abstract String getText();

}
