package com.puzzlebazar.client;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManagerImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyPlace;
import com.philbeaudoin.gwt.presenter.client.proxy.TokenFormatter;
import com.puzzlebazar.client.gin.DefaultPlace;


/**
 * PlaceManager implementation for Puzzlebazar
 */
public class PuzzlebazarPlaceManager extends PlaceManagerImpl {

  private final Provider<ProxyPlace> defaultProxy;

  @Inject
  public PuzzlebazarPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter,
      @DefaultPlace Provider<ProxyPlace> defaultProxy ) {
    super(eventBus, tokenFormatter);
    
    this.defaultProxy = defaultProxy;
  }
  
  @Override
  public void revealDefaultPlace() {
    defaultProxy.get().reveal();
  }

}