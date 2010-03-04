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
import com.puzzlebazar.client.core.presenter.UserSettingsAccountsPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsAccountsProxy extends TabContentProxyImpl<UserSettingsAccountsPresenter> 
implements UserSettingsAccountsPresenter.Proxy {

  private static final String nameToken = "usacc";

  /**
   * A static method to access the name token of this proxy.
   * 
   * @return The name token.
   */
  public static final String getProxyNameToken() {
    return nameToken;
  }
  
  private final Translations translations;
  
  @Inject
  public UserSettingsAccountsProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager, 
      final AsyncProvider<UserSettingsAccountsPresenter> presenter, 
      final Translations translations) {
    super(eventBus, placeManager, new CodeSplitProvider<UserSettingsAccountsPresenter>(presenter, translations), 
        UserSettingsPresenter.TYPE_RequestTabs);

    this.translations = translations;

  }
  
  @Override
  public String getText() {
    return translations.tabAccounts();
  }
  
  @Override
  public String getNameToken() {
    return nameToken;
  }

  @Override
  public float getPriority() {
    return 1;
  }
}