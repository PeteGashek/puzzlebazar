package com.puzzlebazar.shared.action;

import com.philbeaudoin.gwt.dispatch.shared.Result;

public class StringResult implements Result {

  private static final long serialVersionUID = 136945426256971178L;

  private String string;

  @SuppressWarnings("unused")
  private StringResult() {
  }

  public StringResult(final String string) {
    this.string = string;
  }

  public String getString() {
    return string;
  }

}
