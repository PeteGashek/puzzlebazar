/**
 * 
 */
package com.puzzlebazar.client.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContainerProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentHandler;
import com.puzzlebazar.client.presenter.UserSettingsPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsProxy extends TabContainerProxyImpl<UserSettingsPresenter> implements UserSettingsPresenter.Proxy {

  public static final Type<SetContentHandler<?>> TYPE_SetTabContent = new Type<SetContentHandler<?>>();
  
  @Inject
  public UserSettingsProxy(
      final EventBus eventBus, 
      final AsyncProvider<UserSettingsPresenter> presenter,
      final Translations translations) {
    super(eventBus,  new CodeSplitProvider<UserSettingsPresenter>(presenter, translations), TYPE_SetTabContent);
  }
  
}