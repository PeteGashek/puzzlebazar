package com.puzzlebazar.shared.action;

import com.philbeaudoin.gwt.dispatch.shared.Result;

public class GetLoginURLsResult implements Result {

  private static final long serialVersionUID = 7917449246674223581L;

  private String loginURL;
  private String logoutURL;

  @SuppressWarnings("unused")
  private GetLoginURLsResult() {
  }

  public GetLoginURLsResult(final String loginURL, final String logoutURL) {
    this.loginURL = loginURL;
    this.logoutURL = logoutURL;
  }

  public String getLoginURL() {
    return loginURL;
  }

  public String getLogoutURL() {
    return logoutURL;
  }

}
