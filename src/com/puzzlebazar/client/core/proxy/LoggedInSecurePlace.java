package com.puzzlebazar.client.core.proxy;

import com.philbeaudoin.gwt.presenter.client.proxy.PlaceImpl;
import com.puzzlebazar.client.CurrentUser;

/**
 * A secured {@link Place} that can only be accessed when the user
 * is logged in.
 * 
 * @author Philippe Beaudoin
 */
public class LoggedInSecurePlace extends PlaceImpl {  
  
  private final CurrentUser currentUser;

  public LoggedInSecurePlace(
      final String nameToken,
      final CurrentUser currentUser ) {
    super(nameToken);
    this.currentUser = currentUser;
  }

  @Override
  public boolean canReveal() {
    return currentUser.isLoggedIn();
  }
  
}