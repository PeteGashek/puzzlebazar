/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContainerProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentHandler;
import com.puzzlebazar.client.Bundle;
import com.puzzlebazar.client.core.presenter.AdminPresenter;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.resources.Translations;

public class AdminProxy extends TabContainerProxyImpl<AdminPresenter> implements AdminPresenter.Proxy {

  public static final Type<SetContentHandler<?>> TYPE_SetTabContent = new Type<SetContentHandler<?>>();
  
  @Inject
  public AdminProxy(
      final EventBus eventBus, 
      final AsyncProvider<TabbedPresenterBundle> presenterBundle,
      final Translations translations) {
    super(eventBus,  
        new Bundle.CodeSplitProvider<AdminPresenter>(
            presenterBundle,
            TabbedPresenterBundle.ID_AdminPresenter,
            translations), 
        TYPE_SetTabContent);
  }
  
}