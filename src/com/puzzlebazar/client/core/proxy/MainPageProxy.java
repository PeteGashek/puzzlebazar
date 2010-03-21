/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.CodeSplitProvider;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceImpl;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyPlace;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;

public class MainPageProxy 
extends ProxyPlace<MainPagePresenter> 
implements MainPagePresenter.MyProxy {
 
  public static class WrappedProxy extends ProxyImpl<MainPagePresenter> {
    @Inject
    public WrappedProxy(EventBus eventBus,
        final AsyncProvider<MainPagePresenter> presenter) {
      super.presenter = new CodeSplitProvider<MainPagePresenter>(presenter);
    }
  }

  @Inject
  public MainPageProxy(
      final WrappedProxy wrappedProxy ) {
    super.proxy = wrappedProxy;
    super.place = new PlaceImpl( NameTokens.getMainPage() );
  }
  
}