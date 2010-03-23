package com.puzzlebazar.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {
  
  @Source("logo.png")
  ImageResource logo();
  
  @Source("Puzzlebazar.css")
  Style style();

  @Source("defines.css")
  Defines defines();

  public interface Style extends CssResource {
    String username();
    String formMargin();
    String errorText();
    String round1();
    String round2();
    String round4();
    String gray();
    String black();
    String yellow();
    String blue();
    String transparent();
    String vertex();
    String edge();
    String cell();
    String selectedCell();
  }  

  public interface Defines extends CssResource {
    String darkPanelColor();
    String lightPanelColor();
    String titleFontWeight();
    String titleFontSize();
    String titleColor();
    String formLineSpacing();
  }  

}
