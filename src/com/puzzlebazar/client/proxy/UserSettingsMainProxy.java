/**
 * 
 */
package com.puzzlebazar.client.proxy;

import com.google.inject.Inject;
import com.google.inject.Provider;
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
  public UserSettingsMainProxy(final EventBus eventBus, final PlaceManager placeManager,
      final Provider<UserSettingsMainPresenter> presenter, final Translations translations) {
    super(eventBus, placeManager, presenter,
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
  
  @Override
  public void onPresenterRevealed() {
    super.onPresenterRevealed();
    // TODO Temporary, just a demonstration
    placeManager.setOnLeaveConfirmation( "You are about to navigate away from this page. This happened because you " +
        "tried to go to another page in this application, a URL outside this application, or because you" +
        "closed the browser window. Do you want to do this?" );
  }  
  
}