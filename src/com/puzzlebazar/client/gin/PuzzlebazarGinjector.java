package com.puzzlebazar.client.gin;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;

import com.philbeaudoin.platform.dispatch.client.DispatchAsync;
import com.philbeaudoin.platform.dispatch.client.gin.StandardDispatchModule;

import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceManager;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyFailureHandler;

import com.puzzlebazar.client.core.presenter.MainPagePresenter;
import com.puzzlebazar.client.core.presenter.PagePresenter;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.resources.Resources;

@GinModules({ StandardDispatchModule.class, PuzzlebazarClientModule.class })
public interface PuzzlebazarGinjector extends Ginjector {

  PlaceManager getPlaceManager();
  EventBus getEventBus();
  Resources getResources();
  DispatchAsync getDispatcher();
  ProxyFailureHandler getProxyFailureHandler();
  
  Provider<PagePresenter> getPagePresenter();
  AsyncProvider<SplitMainPresenter> getSplitMainPresenter();
  AsyncProvider<TabbedPresenterBundle> getTabbedPresenterBundle();
  Provider<MainPagePresenter> getMainPagePresenter();
  AsyncProvider<PuzzlePresenter> getPuzzlePresenter();
}