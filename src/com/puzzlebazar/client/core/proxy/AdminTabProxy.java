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
import com.puzzlebazar.client.core.presenter.AdminTabPresenter;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.resources.Translations;

public class AdminTabProxy extends TabContainerProxyImpl<AdminTabPresenter> implements AdminTabPresenter.MyProxy {

  public static final Type<RevealContentHandler<?>> TYPE_RevealTabContent = new Type<RevealContentHandler<?>>();

  @Inject
  public AdminTabProxy(
      final AsyncProvider<TabbedPresenterBundle> presenterBundle,
      final Translations translations) {
    this.presenter = 
        new CodeSplitBundleProvider<AdminTabPresenter,TabbedPresenterBundle>(
            presenterBundle,
            TabbedPresenterBundle.ID_AdminPresenter);
    this.revealTabContentEventType = TYPE_RevealTabContent;
  }

}