package com.puzzlebazar.client;

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.proxy.Place;
import com.philbeaudoin.gwtp.mvp.client.proxy.PlaceFactory;

/**
 * The factory that provides {@link LoggedInSecurePlace}.
 * 
 * @author Philippe Beaudoin
 */
public class LoggedInSecurePlaceFactory implements PlaceFactory {

  private final CurrentUser currentUser;

  @Inject
  public LoggedInSecurePlaceFactory( CurrentUser currentUser ) {
    this.currentUser = currentUser;
  }

  @Override
  public Place create(String nameToken) {
    return new LoggedInSecurePlace( nameToken, currentUser );
  }

}