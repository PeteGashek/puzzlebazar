package com.puzzlebazar.shared.action;

import com.philbeaudoin.gwt.dispatch.shared.Action;

public class DoLogout implements Action< StringResult > {

  private static final long serialVersionUID = 4989676825470124216L;
  
  private String currentURL;

  @SuppressWarnings("unused")
  private DoLogout() {
    // For serialization only
  }
  
  public DoLogout( String currentURL ) {
    this.currentURL = currentURL;
  }
  
  public String getCurrentURL() {
    return currentURL;
  }  
  
  
}
