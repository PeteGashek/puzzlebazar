package com.puzzlebazar.shared.action;

import com.philbeaudoin.gwt.dispatch.shared.Action;

public class DoLogin implements Action< StringResult > {

  private static final long serialVersionUID = 8148847315614152818L;
  
  private String currentURL;

  @SuppressWarnings("unused")
  private DoLogin() {
    // For serialization only
  }
  
  public DoLogin( String currentURL ) {
    this.currentURL = currentURL;
  }
  
  public String getCurrentURL() {
    return currentURL;
  }  
  
  
}
