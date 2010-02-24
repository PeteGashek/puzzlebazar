package com.puzzlebazar.client.place;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.place.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.place.PresenterPlace;
import com.puzzlebazar.client.presenter.UserSettingsDetailsPresenter;

public class UserSettingsDetailsPlace extends PresenterPlace<UserSettingsDetailsPresenter.Wrapper, UserSettingsDetailsPresenter> {
  
  @Inject
  public UserSettingsDetailsPlace(EventBus eventBus, PlaceManager placeManager, 
      Provider<UserSettingsDetailsPresenter.Wrapper> wrapper ) {
    super(eventBus, placeManager, wrapper);
    bind();
  }

  @Override
  public String getHistoryToken() {
    return "Details";
  }

}
