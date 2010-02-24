package com.puzzlebazar.client.place;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.place.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.place.PresenterPlace;
import com.puzzlebazar.client.presenter.UserSettingsMainPresenter;

public class UserSettingsMainPlace extends PresenterPlace<UserSettingsMainPresenter.Wrapper, UserSettingsMainPresenter> {
  
  @Inject
  public UserSettingsMainPlace(EventBus eventBus, PlaceManager placeManager, 
      Provider<UserSettingsMainPresenter.Wrapper> wrapper ) {
    super(eventBus, placeManager, wrapper);
    bind();
  }

  @Override
  public String getHistoryToken() {
    return "Settings";
  }

}
