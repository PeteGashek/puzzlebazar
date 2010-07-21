package com.puzzlebazar.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

/**
 * The factory that provides {@link LoggedInSecurePlace}.
 * 
 * @author Philippe Beaudoin
 */
@Singleton
public class LoggedInGatekeeper implements Gatekeeper {

  private final CurrentUser currentUser;

  @Inject
  public LoggedInGatekeeper( CurrentUser currentUser ) {
    this.currentUser = currentUser;
  }

  @Override
  public boolean canReveal() {
    return currentUser.isLoggedIn();
  }

}