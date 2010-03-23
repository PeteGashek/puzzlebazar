package com.puzzlebazar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.philbeaudoin.gwtp.mvp.client.DelayedBindRegistry;
import com.puzzlebazar.client.gin.PuzzlebazarGinjector;

public class Puzzlebazar implements EntryPoint {
  public final PuzzlebazarGinjector ginjector = GWT.create(PuzzlebazarGinjector.class);

  public void onModuleLoad() {

    DelayedBindRegistry.bind(ginjector);
    
    ginjector.getResources().style().ensureInjected();
    ginjector.getPlaceManager().revealCurrentPlace();

  }

}
