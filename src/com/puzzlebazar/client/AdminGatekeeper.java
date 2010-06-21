package com.puzzlebazar.client;

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.proxy.Gatekeeper;

/**
 * The factory that provides {@link AdminSecurePlace}.
 * 
 * @author Philippe Beaudoin
 */
public class AdminGatekeeper implements Gatekeeper {

  private final CurrentUser currentUser;

  @Inject
  public AdminGatekeeper( CurrentUser currentUser ) {
    this.currentUser = currentUser;
  }

  @Override
  public boolean canReveal() {
    return currentUser.isAdministrator();
  }

}