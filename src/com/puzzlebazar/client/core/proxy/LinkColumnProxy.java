/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.DirectProvider;
import com.philbeaudoin.gwt.presenter.client.proxy.PresenterProxyImpl;
import com.puzzlebazar.client.core.presenter.LinkColumnPresenter;

public class LinkColumnProxy extends PresenterProxyImpl<LinkColumnPresenter> implements LinkColumnPresenter.Proxy {
  @Inject
  public LinkColumnProxy(EventBus eventBus, Provider<LinkColumnPresenter> presenter) {
    super(eventBus, new DirectProvider<LinkColumnPresenter>(presenter) );
  }
}