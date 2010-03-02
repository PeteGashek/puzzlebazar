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
    String horizontalBar();
    String round1();
    String round2();
    String round4();
    String roundtab_active();
    String roundtab_inactive();
    String roundtab();    
    String roundtab_inner();
    String simpletab_container();
    String simpletab_bar();    
    String simpletab_panel();    
    String simpletab();    
    String simpletab_active();    
    String simpletab_inactive();    
  }  
}
