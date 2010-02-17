package com.puzzlebazar.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import net.customware.gwt.dispatch.client.gin.ClientDispatchModule;

import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.place.PlaceManager;

import com.puzzlebazar.client.presenter.AppPresenter;
import com.puzzlebazar.client.resources.Resources;

@GinModules({ ClientDispatchModule.class, GreetingClientModule.class })
public interface GreetingGinjector extends Ginjector {

  AppPresenter getAppPresenter();
  PlaceManager getPlaceManager();
  EventBus getEventBus();
  Resources getResources();

}