package com.puzzlebazar.client.proxy;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.DefaultPlaceManager;
import com.philbeaudoin.gwt.presenter.client.proxy.TokenFormatter;


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