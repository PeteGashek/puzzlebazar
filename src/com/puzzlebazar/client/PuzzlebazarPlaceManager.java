package com.puzzlebazar.client;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.Callback;
import com.philbeaudoin.gwt.presenter.client.proxy.CallbackProvider;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManagerImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyBase;
import com.philbeaudoin.gwt.presenter.client.proxy.TokenFormatter;
import com.puzzlebazar.client.gin.DefaultPlace;
import com.puzzlebazar.client.resources.Translations;


/**
 * PlaceManager implementation for Puzzlebazar
 */
public class PuzzlebazarPlaceManager extends PlaceManagerImpl {

  private final CallbackProvider<ProxyBase> defaultProxy;

  @Inject
  public PuzzlebazarPlaceManager(
      final EventBus eventBus, 
      final TokenFormatter tokenFormatter,
      @DefaultPlace final AsyncProvider<ProxyBase> defaultProxy,
      final Translations translations ) {
    super(eventBus, tokenFormatter);
    
    this.defaultProxy = new CodeSplitProvider<ProxyBase>(defaultProxy, translations);
  }
  
  @Override
  public void revealDefaultPlace() {
    defaultProxy.get( new Callback<ProxyBase>(){
      @Override
      public void execute(ProxyBase proxy) {
        proxy.reveal();
      }
    } );
  }

}