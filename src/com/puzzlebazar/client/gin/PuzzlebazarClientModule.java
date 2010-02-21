package com.puzzlebazar.client.gin;

import com.philbeaudoin.gwt.presenter.client.DefaultEventBus;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.gin.AbstractPresenterModule;
import com.philbeaudoin.gwt.presenter.client.place.ParameterTokenFormatter;
import com.philbeaudoin.gwt.presenter.client.place.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.place.TokenFormatter;

import com.puzzlebazar.client.CachingDispatchAsync;
import com.puzzlebazar.client.gin.annotations.DefaultMainPresenter;
import com.puzzlebazar.client.place.PuzzlebazarPlaceManager;
import com.puzzlebazar.client.presenter.AppPresenter;
import com.puzzlebazar.client.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.presenter.SplitMainPresenter;
import com.puzzlebazar.client.presenter.TopBarPresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.view.AppView;
import com.puzzlebazar.client.view.LinkColumnView;
import com.puzzlebazar.client.view.SplitMainView;
import com.puzzlebazar.client.view.TopBarView;

import com.google.inject.Singleton;

public class PuzzlebazarClientModule extends AbstractPresenterModule {

 @Override
 protected void configure() {  
   
   
   bind(Resources.class).in(Singleton.class);
   bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
   bind(PlaceManager.class).to(PuzzlebazarPlaceManager.class);
   bind(TokenFormatter.class).to(ParameterTokenFormatter.class);
   bindPresenter(AppPresenter.class,AppPresenter.Display.class, AppView.class);
   bind(Presenter.class).annotatedWith(DefaultMainPresenter.class).to(SplitMainPresenter.class);      
   bindPresenter(SplitMainPresenter.class,SplitMainPresenter.Display.class, SplitMainView.class);   
   bindPresenter(LinkColumnPresenter.class,LinkColumnPresenter.Display.class, LinkColumnView.class);
   bindPresenter(TopBarPresenter.class,TopBarPresenter.Display.class, TopBarView.class);
   bind(CachingDispatchAsync.class);
//   bind(GreetingPresenterPlace.class).in(Singleton.class);
//   bind(GreetingResponsePresenterPlace.class).in(Singleton.class);
   
 
 }
}
