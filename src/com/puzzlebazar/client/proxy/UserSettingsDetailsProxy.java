/**
 * 
 */
package com.puzzlebazar.client.proxy;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.puzzlebazar.client.presenter.UserSettingsDetailsPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsDetailsProxy extends BasicTabContentProxy<UserSettingsDetailsPresenter> 
implements UserSettingsDetailsPresenter.Proxy {
  @Inject
  public UserSettingsDetailsProxy(final EventBus eventBus, final PlaceManager placeManager, 
      final Provider<UserSettingsDetailsPresenter> presenter, 
      final Translations translations) {
    super(eventBus, placeManager, presenter, translations);
  }
  @Override
  public String getText() {
    return translations.details();
  }
  @Override
  public String getHistoryToken() {
    return "Details";
  }
}