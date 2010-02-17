package com.puzzlebazar.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {
  
  @Source("logo.png")
  ImageResource logo();
  
  @Source("Puzzlebazar.css")
  Style style();

  public interface Style extends CssResource {
    String username();
  }  
}
