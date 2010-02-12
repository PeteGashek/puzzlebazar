package com.puzzlebazar.client.place;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.gin.ProvidedPresenterPlace;
import com.puzzlebazar.client.presenter.GreetingResponsePresenter;

public class GreetingResponsePresenterPlace extends ProvidedPresenterPlace<GreetingResponsePresenter> {

  @Inject
  public GreetingResponsePresenterPlace(Provider<GreetingResponsePresenter> presenter) {
    super(presenter);
  }

  @Override
  public String getName() {
    return "GreetingResponse";
  }

}
