package com.puzzlebazar.shared.action;

import com.philbeaudoin.platform.dispatch.shared.Result;
import com.puzzlebazar.shared.model.User;

public class GetUserResult implements Result {

  private static final long serialVersionUID = 1958970407328919530L;

  private User userInfo;

  @SuppressWarnings("unused")
  private GetUserResult() {
  }

  public GetUserResult(final User userInfo) {
    this.userInfo = userInfo;
  }

  public User getUser() {
    return userInfo;
  }

}
