package com.puzzlebazar.client.gin;

import com.philbeaudoin.gwt.presenter.client.DefaultEventBus;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.gin.AbstractPresenterModule;
import com.philbeaudoin.gwt.presenter.client.proxy.ParameterTokenFormatter;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyPlace;
import com.philbeaudoin.gwt.presenter.client.proxy.TokenFormatter;

import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.PuzzlebazarPlaceManager;
import com.puzzlebazar.client.core.presenter.AdminGeneralPresenter;
import com.puzzlebazar.client.core.presenter.AdminPresenter;
import com.puzzlebazar.client.core.presenter.AdminUsersPresenter;
import com.puzzlebazar.client.core.presenter.AppPresenter;
import com.puzzlebazar.client.core.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.core.presenter.TopBarPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsAccountsPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsPresenter;
import com.puzzlebazar.client.core.proxy.AdminGeneralProxy;
import com.puzzlebazar.client.core.proxy.AdminProxy;
import com.puzzlebazar.client.core.proxy.AdminUsersProxy;
import com.puzzlebazar.client.core.proxy.AppProxy;
import com.puzzlebazar.client.core.proxy.LinkColumnProxy;
import com.puzzlebazar.client.core.proxy.MainPageProxy;
import com.puzzlebazar.client.core.proxy.SplitMainProxy;
import com.puzzlebazar.client.core.proxy.TopBarProxy;
import com.puzzlebazar.client.core.proxy.UserSettingsAccountsProxy;
import com.puzzlebazar.client.core.proxy.UserSettingsGeneralProxy;
import com.puzzlebazar.client.core.proxy.UserSettingsProxy;
import com.puzzlebazar.client.core.view.AdminGeneralView;
import com.puzzlebazar.client.core.view.AdminUsersView;
import com.puzzlebazar.client.core.view.AdminView;
import com.puzzlebazar.client.core.view.AppView;
import com.puzzlebazar.client.core.view.LinkColumnView;
import com.puzzlebazar.client.core.view.MainPageView;
import com.puzzlebazar.client.core.view.SplitMainView;
import com.puzzlebazar.client.core.view.TopBarView;
import com.puzzlebazar.client.core.view.UserSettingsAccountsView;
import com.puzzlebazar.client.core.view.UserSettingsGeneralView;
import com.puzzlebazar.client.core.view.UserSettingsView;
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
    bind(ProxyPlace.class).annotatedWith(DefaultPlace.class).to(MainPageProxy.class);
    bindPresenter(AppPresenter.class,AppPresenter.Display.class, AppView.class, AppPresenter.Proxy.class, AppProxy.class);
    bindPresenter(SplitMainPresenter.class, SplitMainPresenter.Display.class, SplitMainView.class, SplitMainPresenter.Proxy.class, SplitMainProxy.class );
    bindPresenter(LinkColumnPresenter.class, LinkColumnPresenter.Display.class, LinkColumnView.class, LinkColumnPresenter.Proxy.class, LinkColumnProxy.class );
    bindPresenter(TopBarPresenter.class, TopBarPresenter.Display.class, TopBarView.class, TopBarPresenter.Proxy.class, TopBarProxy.class );
    bindPresenter(UserSettingsPresenter.class, UserSettingsPresenter.Display.class, UserSettingsView.class, UserSettingsPresenter.Proxy.class, UserSettingsProxy.class );
    bindPresenter(UserSettingsGeneralPresenter.class, UserSettingsGeneralPresenter.Display.class, UserSettingsGeneralView.class, UserSettingsGeneralPresenter.Proxy.class, UserSettingsGeneralProxy.class);
    bindPresenter(UserSettingsAccountsPresenter.class, UserSettingsAccountsPresenter.Display.class, UserSettingsAccountsView.class, UserSettingsAccountsPresenter.Proxy.class, UserSettingsAccountsProxy.class);
    bindPresenter(AdminPresenter.class, AdminPresenter.Display.class, AdminView.class, AdminPresenter.Proxy.class, AdminProxy.class );
    bindPresenter(AdminGeneralPresenter.class, AdminGeneralPresenter.Display.class, AdminGeneralView.class, AdminGeneralPresenter.Proxy.class, AdminGeneralProxy.class);
    bindPresenter(AdminUsersPresenter.class, AdminUsersPresenter.Display.class, AdminUsersView.class, AdminUsersPresenter.Proxy.class, AdminUsersProxy.class);
    bindPresenter(MainPagePresenter.class, MainPagePresenter.Display.class, MainPageView.class, MainPagePresenter.Proxy.class, MainPageProxy.class);
    
  }
}
