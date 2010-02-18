package com.puzzlebazar.shared.action;

import com.philbeaudoin.gwt.dispatch.shared.Action;

public class Login implements Action<LoginResult> {

  private static final long serialVersionUID = 5804421607858017477L;

  private String name;

  @SuppressWarnings("unused")
  private Login() {
  }

  public Login(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
