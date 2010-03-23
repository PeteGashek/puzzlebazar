package com.puzzlebazar.shared.action;

import com.philbeaudoin.gwtp.dispatch.shared.Action;
import com.puzzlebazar.shared.model.User;

public class EditUser implements Action< NoResult > {

  private static final long serialVersionUID = -1748598990259084358L;

  private User user;
  private User previousUser = null;

  @SuppressWarnings("unused")
  private EditUser() {
    // For serialization only
  }

  public EditUser( User user ) {
    this.user = user;
  }

  public void setPreviousUser( final User previousUser ) {
    this.previousUser = previousUser;
  }
  
  public User getUser() {
    return user;
  }
  
  public User getPreviousUser() {
    return previousUser;
  }
}
