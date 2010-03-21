/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.CodeSplitBundleProvider;
import com.philbeaudoin.platform.mvp.client.proxy.TabContainerProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentHandler;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.core.presenter.UserSettingsTabPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsTabProxy extends TabContainerProxyImpl<UserSettingsTabPresenter> implements UserSettingsTabPresenter.MyProxy {

  public static final Type<RevealContentHandler<?>> TYPE_RevealTabContent = new Type<RevealContentHandler<?>>();

  @Inject
  public UserSettingsTabProxy(
      final AsyncProvider<TabbedPresenterBundle> presenterBundle,
      final Translations translations) {
    super.presenter = new CodeSplitBundleProvider<UserSettingsTabPresenter,TabbedPresenterBundle>(
            presenterBundle,
            TabbedPresenterBundle.ID_UserSettingsPresenter);
    super.revealTabContentEventType = TYPE_RevealTabContent;
  }

}