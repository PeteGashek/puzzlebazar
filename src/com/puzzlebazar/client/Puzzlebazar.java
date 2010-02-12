package com.puzzlebazar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.puzzlebazar.client.gin.GreetingGinjector;

public class Puzzlebazar implements EntryPoint {
 private final GreetingGinjector injector = GWT.create(GreetingGinjector.class);

 public void onModuleLoad() {
  injector.getAppPresenter();

  injector.getPlaceManager().fireCurrentPlace();
 }
}
