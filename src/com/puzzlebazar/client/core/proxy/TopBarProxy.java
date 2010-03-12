/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.DirectProvider;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyImpl;
import com.puzzlebazar.client.core.presenter.TopBarPresenter;

public class TopBarProxy extends ProxyImpl<TopBarPresenter> implements TopBarPresenter.MyProxy {
  @Inject
  public TopBarProxy(EventBus eventBus, Provider<TopBarPresenter> presenter) {
    super(eventBus, new DirectProvider<TopBarPresenter>(presenter) );
  }
}