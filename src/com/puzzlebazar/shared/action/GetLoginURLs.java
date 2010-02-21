package com.puzzlebazar.shared.action;

import com.philbeaudoin.gwt.dispatch.shared.Action;

public class GetLoginURLs implements Action<GetLoginURLsResult> {

  private static final long serialVersionUID = 5804421607858017477L;

  private String href;
  
  @SuppressWarnings("unused")
  private GetLoginURLs() {
    // For serialization only
  }

  public GetLoginURLs( String href ) {
    this.href = href;
  }

  public String getHref() {
    return href;
  }
  
}
