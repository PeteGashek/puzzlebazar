/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.puzzlebazar.client.CodeSplitProvider;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsGeneralProxy extends TabContentProxyImpl<UserSettingsGeneralPresenter> implements UserSettingsGeneralPresenter.Proxy {
  
  private static final String historyToken = "usgen";

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
  public UserSettingsGeneralProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final AsyncProvider<UserSettingsGeneralPresenter> presenter, 
      final Translations translations) {
    super(eventBus, placeManager, new CodeSplitProvider<UserSettingsGeneralPresenter>(presenter, translations),
        UserSettingsPresenter.TYPE_RequestTabs);
    
    this.translations = translations;
  }

  @Override
  public String getText() {
    return translations.tabGeneral();
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