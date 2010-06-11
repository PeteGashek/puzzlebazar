package com.puzzlebazar.client;

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.proxy.Place;
import com.philbeaudoin.gwtp.mvp.client.proxy.PlaceFactory;

/**
 * The factory that provides {@link AdminSecurePlace}.
 * 
 * @author Philippe Beaudoin
 */
public class AdminSecurePlaceFactory implements PlaceFactory {

  private final CurrentUser currentUser;

  @Inject
  public AdminSecurePlaceFactory( CurrentUser currentUser ) {
    this.currentUser = currentUser;
  }

  @Override
  public Place create(String nameToken) {
    return new AdminSecurePlace( nameToken, currentUser );
  }

}