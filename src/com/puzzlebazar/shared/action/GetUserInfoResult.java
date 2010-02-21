package com.puzzlebazar.shared.action;

import com.philbeaudoin.gwt.dispatch.shared.Result;
import com.puzzlebazar.shared.model.UserInfo;

public class GetUserInfoResult implements Result {

  private static final long serialVersionUID = 1958970407328919530L;

  private UserInfo userInfo;

  @SuppressWarnings("unused")
  private GetUserInfoResult() {
  }

  public GetUserInfoResult(final UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

}
