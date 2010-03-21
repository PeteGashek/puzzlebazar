package com.puzzlebazar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.puzzlebazar.client.gin.PuzzlebazarGinjector;

public class Puzzlebazar implements EntryPoint {
  public static final PuzzlebazarGinjector ginjector = GWT.create(PuzzlebazarGinjector.class);

  public void onModuleLoad() {

    ginjector.getPagePresenterProxy();
    
    ginjector.getResources().style().ensureInjected();
    ginjector.getPlaceManager().revealCurrentPlace();

  }

}
