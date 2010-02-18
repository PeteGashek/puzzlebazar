package com.puzzlebazar.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import com.philbeaudoin.gwt.dispatch.client.gin.StandardDispatchModule;

import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.place.PlaceManager;

import com.puzzlebazar.client.presenter.AppPresenter;
import com.puzzlebazar.client.resources.Resources;

@GinModules({ StandardDispatchModule.class, GreetingClientModule.class })
public interface GreetingGinjector extends Ginjector {

  AppPresenter getAppPresenter();
  PlaceManager getPlaceManager();
  EventBus getEventBus();
  Resources getResources();

}