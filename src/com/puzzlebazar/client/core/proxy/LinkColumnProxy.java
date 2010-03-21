/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.StandardProvider;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyImpl;
import com.puzzlebazar.client.core.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.core.presenter.RevealDefaultLinkColumnEvent;
import com.puzzlebazar.client.core.presenter.RevealDefaultLinkColumnHandler;

public class LinkColumnProxy extends ProxyImpl<LinkColumnPresenter> 
implements LinkColumnPresenter.MyProxy, RevealDefaultLinkColumnHandler {
  @Inject
  public LinkColumnProxy(Provider<LinkColumnPresenter> presenter) {
    this.presenter = new StandardProvider<LinkColumnPresenter>(presenter);
  }

  @Inject
  protected void bind( EventBus eventBus ) {
    eventBus.addHandler( RevealDefaultLinkColumnEvent.getType(), this);
  }

  @Override
  public void onRevealDefaultLinkColumn(RevealDefaultLinkColumnEvent event) {

    getPresenter( new AsyncCallback<LinkColumnPresenter>() {
      @Override
      public void onFailure(Throwable caught) {
        failureHandler.onFailedGetPresenter(caught);
      }

      @Override
      public void onSuccess(LinkColumnPresenter presenter) {
        presenter.reveal();
      }
    } );
  }
}