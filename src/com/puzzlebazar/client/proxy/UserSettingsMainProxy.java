/**
 * 
 */
package com.puzzlebazar.client.proxy;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.puzzlebazar.client.presenter.UserSettingsMainPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsMainProxy extends BasicTabContentProxy<UserSettingsMainPresenter> implements UserSettingsMainPresenter.Proxy {
  @Inject
  public UserSettingsMainProxy(final EventBus eventBus, final PlaceManager placeManager,
      final Provider<UserSettingsMainPresenter> presenter, final Translations translations) {
    super(eventBus, placeManager, presenter, translations);
  }
  @Override
  public String getText() {
    return translations.settings();
  }
  @Override
  public String getHistoryToken() {
    return "Settings";
  }
}