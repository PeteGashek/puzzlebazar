/**
 * 
 */
package com.puzzlebazar.client.proxy;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.puzzlebazar.client.presenter.UserSettingsDetailsPresenter;
import com.puzzlebazar.client.presenter.UserSettingsPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsDetailsProxy extends TabContentProxyImpl<UserSettingsDetailsPresenter> 
implements UserSettingsDetailsPresenter.Proxy {

  private static final String historyToken = "Details";

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
  public UserSettingsDetailsProxy(final EventBus eventBus, final PlaceManager placeManager, 
      final Provider<UserSettingsDetailsPresenter> presenter, 
      final Translations translations) {
    super(eventBus, placeManager, presenter, 
        UserSettingsPresenter.TYPE_RequestTabs);

    this.translations = translations;

  }
  
  @Override
  public String getText() {
    return translations.details();
  }
  
  @Override
  public String getHistoryToken() {
    return historyToken;
  }

  @Override
  public float getPriority() {
    return 1;
  }
}