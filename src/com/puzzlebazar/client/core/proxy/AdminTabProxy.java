/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.CodeSplitBundleProvider;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyFailureHandler;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentHandler;
import com.puzzlebazar.client.core.presenter.AdminTabPresenter;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.resources.Translations;

public class AdminTabProxy extends ProxyImpl<AdminTabPresenter> implements AdminTabPresenter.MyProxy {

  private RevealContentHandler<AdminTabPresenter> revealContentHandler = null;

  @Inject
  public AdminTabProxy(
      final AsyncProvider<TabbedPresenterBundle> presenterBundle,
      final Translations translations) {
    this.presenter = 
        new CodeSplitBundleProvider<AdminTabPresenter,TabbedPresenterBundle>(
            presenterBundle,
            TabbedPresenterBundle.ID_AdminPresenter);
  }

  @Inject
  protected void bind(ProxyFailureHandler failureHandler, EventBus eventBus) {
    revealContentHandler  = new RevealContentHandler<AdminTabPresenter>( failureHandler, this );    
    eventBus.addHandler( AdminTabPresenter.TYPE_RevealTabContent, revealContentHandler );
  }

  
}