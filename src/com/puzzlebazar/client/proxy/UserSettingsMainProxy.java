/**
 * 
 */
package com.puzzlebazar.client.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.puzzlebazar.client.presenter.UserSettingsMainPresenter;
import com.puzzlebazar.client.presenter.UserSettingsPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsMainProxy extends TabContentProxyImpl<UserSettingsMainPresenter> implements UserSettingsMainPresenter.Proxy {
  
  private static final String historyToken = "Settings";

  /**
   * A static method to access the history token of this proxy
   * 
   * @return The history token.
   */
  public static final String getProxyHistoryToken() {
    return historyToken;
  }
  
  private final Translations translations;  
  
  @Inject
  public UserSettingsMainProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final AsyncProvider<UserSettingsMainPresenter> presenter, 
      final Translations translations) {
    super(eventBus, placeManager, new CodeSplitProvider<UserSettingsMainPresenter>(presenter, translations),
        UserSettingsPresenter.TYPE_RequestTabs);
    
    this.translations = translations;
  }

  @Override
  public String getText() {
    return translations.settings();
  }
  
  @Override
  public String getHistoryToken() {
    return historyToken;
  }
  
  @Override
  public float getPriority() {
    return 0;
  }
    
}