package com.puzzlebazar.client.ui;

import com.google.gwt.user.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;

public class ImageHyperlink extends Hyperlink {
  
  public ImageHyperlink(){
  }

  public void setResource(ImageResource imageResource){
    Image img = new Image(imageResource);
    Element anchor = DOM.getFirstChild(getElement());
    anchor.appendChild(img.getElement());
  }
}
