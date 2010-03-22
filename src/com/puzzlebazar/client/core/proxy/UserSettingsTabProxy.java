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
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.core.presenter.UserSettingsTabPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsTabProxy extends ProxyImpl<UserSettingsTabPresenter> implements UserSettingsTabPresenter.MyProxy {

  private RevealContentHandler<UserSettingsTabPresenter> revealContentHandler;

  @Inject
  public UserSettingsTabProxy(
      final AsyncProvider<TabbedPresenterBundle> presenterBundle,
      final Translations translations) {
    super.presenter = new CodeSplitBundleProvider<UserSettingsTabPresenter,TabbedPresenterBundle>(
            presenterBundle,
            TabbedPresenterBundle.ID_UserSettingsPresenter);
  }

  @Inject
  protected void bind(ProxyFailureHandler failureHandler, EventBus eventBus) {
    revealContentHandler  = new RevealContentHandler<UserSettingsTabPresenter>( failureHandler, this );    
    eventBus.addHandler( UserSettingsTabPresenter.TYPE_RevealTabContent, revealContentHandler );
  }
}