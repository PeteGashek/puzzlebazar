package com.puzzlebazar.shared.action;

import com.philbeaudoin.gwt.dispatch.shared.Result;
import com.puzzlebazar.shared.model.User;

public class GetUserInfoResult implements Result {

  private static final long serialVersionUID = 1958970407328919530L;

  private User userInfo;

  @SuppressWarnings("unused")
  private GetUserInfoResult() {
  }

  public GetUserInfoResult(final User userInfo) {
    this.userInfo = userInfo;
  }

  public User getUserInfo() {
    return userInfo;
  }

}
