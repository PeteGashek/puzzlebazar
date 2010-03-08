/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.DirectProvider;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyImpl;
import com.puzzlebazar.client.core.presenter.TopBarPresenter;

public class TopBarProxy extends ProxyImpl<TopBarPresenter> implements TopBarPresenter.MyProxy {
  @Inject
  public TopBarProxy(EventBus eventBus, Provider<TopBarPresenter> presenter) {
    super(eventBus, new DirectProvider<TopBarPresenter>(presenter) );
  }
}