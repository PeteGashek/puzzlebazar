package com.puzzlebazar.client.place;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.gin.ProvidedPresenterPlace;
import com.philbeaudoin.gwt.presenter.client.place.PlaceManager;
import com.puzzlebazar.client.presenter.UserSettingsPresenter;

public class UserSettingsPresenterPlace extends ProvidedPresenterPlace<UserSettingsPresenter> {
  
  @Inject
  public UserSettingsPresenterPlace(Provider<UserSettingsPresenter> presenter, PlaceManager placeManager ) {
    super(presenter, placeManager);
  }

  @Override
  public String getName() {
    return "Settings";
  }
  
}
