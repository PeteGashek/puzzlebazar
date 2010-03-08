/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyPlace;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.puzzlebazar.client.CodeSplitProvider;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;
import com.puzzlebazar.client.resources.Translations;

public class MainPageProxy 
extends ProxyPlace<MainPagePresenter> 
implements MainPagePresenter.MyProxy {
 
  public static class WrappedProxy extends ProxyImpl<MainPagePresenter> {
    @Inject
    public WrappedProxy(EventBus eventBus,
        final AsyncProvider<MainPagePresenter> presenter,
        final Translations translations) {
      super(
        eventBus,
        new CodeSplitProvider<MainPagePresenter>(presenter, translations));
    }
  }

  @Inject
  public MainPageProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager, 
      final WrappedProxy wrappedProxy ) {
    super(
        eventBus, 
        placeManager, 
        wrappedProxy,
        new PlaceImpl( NameTokens.getMainPage() ) );
  }
  
}