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
    String splitMainView();
    String round1();
    String round2();
    String round4();
    String active();
    String inactive();
    String tab();    
    String tab_inner();    
    String horizontalBar();
  }  
}
