package com.puzzlebazar.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface Resources extends ClientBundle {

  @Source("Puzzlebazar.css")
  Style style();

  public interface Style extends CssResource {
    String dialogVPanel();
    String serverResponseLabelError();
    String closeButton();
  }  
}
