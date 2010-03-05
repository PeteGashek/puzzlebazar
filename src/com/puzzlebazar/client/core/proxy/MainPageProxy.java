/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyPlaceImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.puzzlebazar.client.CodeSplitProvider;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;
import com.puzzlebazar.client.resources.Translations;

public class MainPageProxy extends ProxyPlaceImpl<MainPagePresenter> 
implements MainPagePresenter.Proxy {

  private static final String nameToken = "main";

  /**
   * A static method to access the name token of this proxy.
   * 
   * @return The name token.
   */
  public static final String getProxyNameToken() {
    return nameToken;
  }
  
  @Inject
  public MainPageProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager, 
      final AsyncProvider<MainPagePresenter> presenter, 
      final Translations translations) {
    super(
        eventBus, 
        placeManager, 
        new CodeSplitProvider<MainPagePresenter>(presenter, translations));
  }
  
  @Override
  public String getNameToken() {
    return nameToken;
  }

}