/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.CodeSplitProvider;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyImpl;
import com.puzzlebazar.client.core.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.core.presenter.RevealDefaultLinkColumnEvent;
import com.puzzlebazar.client.core.presenter.RevealDefaultLinkColumnHandler;

public class LinkColumnProxy extends ProxyImpl<LinkColumnPresenter> 
implements LinkColumnPresenter.MyProxy, RevealDefaultLinkColumnHandler {
  @Inject
  public LinkColumnProxy(AsyncProvider<LinkColumnPresenter> presenter) {
    this.presenter = new CodeSplitProvider<LinkColumnPresenter>(presenter);
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