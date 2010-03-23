package com.puzzlebazar.client.gin;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;

import com.philbeaudoin.gwtp.dispatch.client.DispatchAsync;
import com.philbeaudoin.gwtp.dispatch.client.gin.StandardDispatchModule;

import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.proxy.PlaceManager;
import com.philbeaudoin.gwtp.mvp.client.proxy.ProxyFailureHandler;

import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.core.presenter.AdminGeneralPresenter;
import com.puzzlebazar.client.core.presenter.AdminUsersPresenter;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;
import com.puzzlebazar.client.core.presenter.PagePresenter;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.core.presenter.UserSettingsAccountsPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.resources.Translations;

@GinModules({ StandardDispatchModule.class, PuzzlebazarClientModule.class })
public interface PuzzlebazarGinjector extends Ginjector {

  PlaceManager getPlaceManager();
  EventBus getEventBus();
  Resources getResources();
  DispatchAsync getDispatcher();
  ProxyFailureHandler getProxyFailureHandler();
  CurrentUser getCurrentUser();
  Translations getTranslations();
  
  Provider<PagePresenter> getPagePresenter();
  AsyncProvider<SplitMainPresenter> getSplitMainPresenter();
  AsyncProvider<TabbedPresenterBundle> getTabbedPresenterBundle();
  Provider<MainPagePresenter> getMainPagePresenter();
  AsyncProvider<PuzzlePresenter> getPuzzlePresenter();
  AsyncProvider<AdminGeneralPresenter> getAdminGeneralPresenter();
  AsyncProvider<AdminUsersPresenter> getAdminUsersPresenter();
  AsyncProvider<UserSettingsGeneralPresenter> getUserSettingsGeneralPresenter();
  AsyncProvider<UserSettingsAccountsPresenter> getUserSettingsAccountsPresenter();
}