package com.puzzlebazar.client.place;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.place.PlaceManager;
import com.puzzlebazar.client.presenter.UserSettingsMainPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsMainPresenterPlace extends TabPresenterPlace<UserSettingsMainPresenter.Wrapper, UserSettingsMainPresenter> {
  
  @Inject
  public UserSettingsMainPresenterPlace(EventBus eventBus, PlaceManager placeManager, 
      UserSettingsMainPresenter.Wrapper wrapper, Translations translations ) {
    super(eventBus, placeManager, wrapper, translations);
    bind();
  }

  @Override
  public String getName() {
    return "Settings";
  }

  @Override
  public String getText() {
    return translations.settings();
  }
  
}
