/**
 * 
 */
package com.puzzlebazar.client.core.presenter;

/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.CodeSplitProvider;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.proxy.ProxyImpl;

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
        presenter.forceReveal();
      }
    } );
  }
}