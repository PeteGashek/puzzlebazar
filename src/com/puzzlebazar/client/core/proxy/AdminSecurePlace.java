package com.puzzlebazar.client.core.proxy;

import com.philbeaudoin.platform.mvp.client.proxy.PlaceImpl;

import com.puzzlebazar.client.CurrentUser;

/**
 * A secured {@link Place} that can only be accessed by the administrator.
 * 
 * @author Philippe Beaudoin
 */
public class AdminSecurePlace extends PlaceImpl {  
  
  private final CurrentUser currentUser;

  public AdminSecurePlace(
      final String nameToken,
      final CurrentUser currentUser ) {
    super(nameToken);
    this.currentUser = currentUser;
  }

  @Override
  public boolean canReveal() {
    return currentUser.isAdministrator();
  }
  
}