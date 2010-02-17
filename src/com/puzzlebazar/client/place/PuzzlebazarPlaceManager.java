package com.puzzlebazar.client.place;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.place.DefaultPlaceManager;
import com.philbeaudoin.gwt.presenter.client.place.TokenFormatter;


/**
 * PlaceManager implementation for Puzzlebazar
 * 
 *
 */
public class PuzzlebazarPlaceManager extends DefaultPlaceManager{

  @Inject
  public PuzzlebazarPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter) {
    super(eventBus, tokenFormatter);
  }

}