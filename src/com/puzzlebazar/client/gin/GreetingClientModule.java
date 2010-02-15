package com.puzzlebazar.client.gin;

import com.philbeaudoin.gwt.presenter.client.DefaultEventBus;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.gin.AbstractPresenterModule;
import com.philbeaudoin.gwt.presenter.client.place.ParameterTokenFormatter;
import com.philbeaudoin.gwt.presenter.client.place.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.place.TokenFormatter;

import com.puzzlebazar.client.CachingDispatchAsync;
import com.puzzlebazar.client.Resources;
import com.puzzlebazar.client.place.GreetingPresenterPlace;
import com.puzzlebazar.client.place.GreetingResponsePresenterPlace;
import com.puzzlebazar.client.place.PuzzlebazarPlaceManager;
import com.puzzlebazar.client.presenter.AppPresenter;
import com.puzzlebazar.client.presenter.GreetingPresenter;
import com.puzzlebazar.client.presenter.GreetingResponsePresenter;
import com.puzzlebazar.client.view.AppView;
import com.puzzlebazar.client.view.GreetingResponseView;
import com.puzzlebazar.client.view.GreetingView;

import com.google.inject.Singleton;

public class GreetingClientModule extends AbstractPresenterModule {

 @Override
 protected void configure() {  
   
   
   bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
   bind(PlaceManager.class).to(PuzzlebazarPlaceManager.class);
   bind(TokenFormatter.class).to(ParameterTokenFormatter.class);
   bindPresenter(GreetingPresenter.class,GreetingPresenter.Display.class, GreetingView.class);
   bindPresenter(GreetingResponsePresenter.class,GreetingResponsePresenter.Display.class, GreetingResponseView.class);
   bindPresenter(AppPresenter.class,AppPresenter.Display.class, AppView.class);
   bind(CachingDispatchAsync.class);
   bind(GreetingPresenterPlace.class).in(Singleton.class);
   bind(GreetingResponsePresenterPlace.class).in(Singleton.class);
   bind(Resources.class).in(Singleton.class);
   
 
 }
}
