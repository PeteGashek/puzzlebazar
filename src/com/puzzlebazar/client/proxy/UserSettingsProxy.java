/**
 * 
 */
package com.puzzlebazar.client.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentHandler;
import com.puzzlebazar.client.presenter.UserSettingsPresenter;

public class UserSettingsProxy extends BasicTabContainerProxy<UserSettingsPresenter> implements UserSettingsPresenter.Proxy {

  public static final Type<SetContentHandler> TYPE_SetTabContent = new Type<SetContentHandler>();
  
  @Inject
  public UserSettingsProxy(EventBus eventBus, Provider<UserSettingsPresenter> presenter) {
    super(eventBus, presenter, TYPE_SetTabContent);
  }
  
}