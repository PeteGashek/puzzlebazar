/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.Callback;
import com.philbeaudoin.platform.mvp.client.proxy.DirectProvider;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyImpl;
import com.puzzlebazar.client.core.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.core.presenter.RevealDefaultLinkColumnEvent;
import com.puzzlebazar.client.core.presenter.RevealDefaultLinkColumnHandler;

public class LinkColumnProxy extends ProxyImpl<LinkColumnPresenter> 
implements LinkColumnPresenter.MyProxy, RevealDefaultLinkColumnHandler {
  @Inject
  public LinkColumnProxy(EventBus eventBus, Provider<LinkColumnPresenter> presenter) {
    super(eventBus, new DirectProvider<LinkColumnPresenter>(presenter) );
  }

  @Override
  protected void onBind() {
    registerHandler( eventBus.addHandler( RevealDefaultLinkColumnEvent.getType(), this) );
  }

  @Override
  public void onRevealDefaultLinkColumn(RevealDefaultLinkColumnEvent event) {

    getPresenter( new Callback<LinkColumnPresenter>() {
      @Override public void execute(LinkColumnPresenter presenter) {
        presenter.reveal();
      }
    } );
  }
}