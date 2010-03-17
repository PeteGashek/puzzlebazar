package com.puzzlebazar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.puzzlebazar.client.gin.PuzzlebazarGinjector;

public class Puzzlebazar implements EntryPoint {
  private final PuzzlebazarGinjector injector = GWT.create(PuzzlebazarGinjector.class);


  public void onModuleLoad() {

    injector.getResources().style().ensureInjected();
    injector.getPlaceManager().revealCurrentPlace();

  }

}
