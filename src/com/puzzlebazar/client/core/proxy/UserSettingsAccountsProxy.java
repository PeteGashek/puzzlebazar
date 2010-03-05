/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.puzzlebazar.client.CodeSplitProvider;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.core.presenter.UserSettingsAccountsPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsAccountsProxy 
extends UserSettingsTabContentProxy<UserSettingsAccountsPresenter> 
implements UserSettingsAccountsPresenter.Proxy {
  
  @Inject
  public UserSettingsAccountsProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager, 
      final AsyncProvider<UserSettingsAccountsPresenter> presenter, 
      final CurrentUser currentUser,      
      final Translations translations) {
    super(
        eventBus, 
        placeManager, 
        new CodeSplitProvider<UserSettingsAccountsPresenter>(presenter, translations), 
        UserSettingsPresenter.TYPE_RequestTabs,
        currentUser,
        translations);
  }
  
  @Override
  public String getText() {
    return translations.tabAccounts();
  }
  
  @Override
  public String getNameToken() {
    return NameTokens.getUserSettingsAccounts();
  }

  @Override
  public float getPriority() {
    return 1;
  }
}