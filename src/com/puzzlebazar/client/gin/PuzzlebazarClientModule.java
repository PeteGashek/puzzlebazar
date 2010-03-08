package com.puzzlebazar.client.gin;

import com.philbeaudoin.gwt.presenter.client.DefaultEventBus;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.gin.AbstractPresenterModule;
import com.philbeaudoin.gwt.presenter.client.proxy.ParameterTokenFormatter;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyBase;
import com.philbeaudoin.gwt.presenter.client.proxy.TokenFormatter;

import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.PuzzlebazarPlaceManager;
import com.puzzlebazar.client.core.presenter.AdminGeneralPresenter;
import com.puzzlebazar.client.core.presenter.AdminTabPresenter;
import com.puzzlebazar.client.core.presenter.AdminUsersPresenter;
import com.puzzlebazar.client.core.presenter.AppPresenter;
import com.puzzlebazar.client.core.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.core.presenter.TopBarPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsAccountsPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsTabPresenter;
import com.puzzlebazar.client.core.proxy.AdminGeneralProxy;
import com.puzzlebazar.client.core.proxy.AdminTabProxy;
import com.puzzlebazar.client.core.proxy.AdminUsersProxy;
import com.puzzlebazar.client.core.proxy.AppProxy;
import com.puzzlebazar.client.core.proxy.LinkColumnProxy;
import com.puzzlebazar.client.core.proxy.MainPageProxy;
import com.puzzlebazar.client.core.proxy.SplitMainProxy;
import com.puzzlebazar.client.core.proxy.TopBarProxy;
import com.puzzlebazar.client.core.proxy.UserSettingsAccountsProxy;
import com.puzzlebazar.client.core.proxy.UserSettingsGeneralProxy;
import com.puzzlebazar.client.core.proxy.UserSettingsTabProxy;
import com.puzzlebazar.client.core.view.AdminGeneralView;
import com.puzzlebazar.client.core.view.AdminUsersView;
import com.puzzlebazar.client.core.view.AdminTabView;
import com.puzzlebazar.client.core.view.AppView;
import com.puzzlebazar.client.core.view.LinkColumnView;
import com.puzzlebazar.client.core.view.MainPageView;
import com.puzzlebazar.client.core.view.SplitMainView;
import com.puzzlebazar.client.core.view.TopBarView;
import com.puzzlebazar.client.core.view.UserSettingsAccountsView;
import com.puzzlebazar.client.core.view.UserSettingsGeneralView;
import com.puzzlebazar.client.core.view.UserSettingsTabView;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.resources.Translations;

import com.google.inject.Singleton;

public class PuzzlebazarClientModule extends AbstractPresenterModule {

  @Override
  protected void configure() {  

    // Singletons
    bind(Resources.class).in(Singleton.class);
    bind(Translations.class).in(Singleton.class);
    bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
    bind(PlaceManager.class).to(PuzzlebazarPlaceManager.class).in(Singleton.class);
    bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
    bind(CurrentUser.class).asEagerSingleton();
    
    // Presenter bundles
    bind(TabbedPresenterBundle.class).in(Singleton.class);

    // Presenters
    bind(ProxyBase.class).annotatedWith(DefaultPlace.class).to(MainPageProxy.class);
    bindPresenter(AppPresenter.class,AppPresenter.MyDisplay.class, AppView.class, AppPresenter.MyProxy.class, AppProxy.class);
    bindPresenter(SplitMainPresenter.class, SplitMainPresenter.MyDisplay.class, SplitMainView.class, SplitMainPresenter.MyProxy.class, SplitMainProxy.class );
    bindPresenter(LinkColumnPresenter.class, LinkColumnPresenter.MyDisplay.class, LinkColumnView.class, LinkColumnPresenter.MyProxy.class, LinkColumnProxy.class );
    bindPresenter(TopBarPresenter.class, TopBarPresenter.MyDisplay.class, TopBarView.class, TopBarPresenter.MyProxy.class, TopBarProxy.class );
    bindPresenter(UserSettingsTabPresenter.class, UserSettingsTabPresenter.MyDisplay.class, UserSettingsTabView.class, UserSettingsTabPresenter.MyProxy.class, UserSettingsTabProxy.class );
    bindPresenter(UserSettingsGeneralPresenter.class, UserSettingsGeneralPresenter.MyDisplay.class, UserSettingsGeneralView.class, UserSettingsGeneralPresenter.MyProxy.class, UserSettingsGeneralProxy.class);
    bindPresenter(UserSettingsAccountsPresenter.class, UserSettingsAccountsPresenter.MyDisplay.class, UserSettingsAccountsView.class, UserSettingsAccountsPresenter.MyProxy.class, UserSettingsAccountsProxy.class);
    bindPresenter(AdminTabPresenter.class, AdminTabPresenter.MyDisplay.class, AdminTabView.class, AdminTabPresenter.MyProxy.class, AdminTabProxy.class );
    bindPresenter(AdminGeneralPresenter.class, AdminGeneralPresenter.MyDisplay.class, AdminGeneralView.class, AdminGeneralPresenter.MyProxy.class, AdminGeneralProxy.class);
    bindPresenter(AdminUsersPresenter.class, AdminUsersPresenter.MyDisplay.class, AdminUsersView.class, AdminUsersPresenter.MyProxy.class, AdminUsersProxy.class);
    bindPresenter(MainPagePresenter.class, MainPagePresenter.MyDisplay.class, MainPageView.class, MainPagePresenter.MyProxy.class, MainPageProxy.class);
    
  }
}
