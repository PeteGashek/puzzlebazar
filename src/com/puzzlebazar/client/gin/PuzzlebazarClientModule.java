package com.puzzlebazar.client.gin;

import com.philbeaudoin.gwtp.mvp.client.DefaultEventBus;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.gin.AbstractPresenterModule;
import com.philbeaudoin.gwtp.mvp.client.proxy.ParameterTokenFormatter;
import com.philbeaudoin.gwtp.mvp.client.proxy.PlaceManager;
import com.philbeaudoin.gwtp.mvp.client.proxy.ProxyFailureHandler;
import com.philbeaudoin.gwtp.mvp.client.proxy.ProxyRaw;
import com.philbeaudoin.gwtp.mvp.client.proxy.TokenFormatter;
import com.philbeaudoin.gwtp.mvp.client.proxy.RootProxy;

import com.puzzlebazar.client.ActionCallback;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.FailureHandlerAlert;
import com.puzzlebazar.client.PuzzlebazarPlaceManager;
import com.puzzlebazar.client.core.presenter.AdminGeneralPresenter;
import com.puzzlebazar.client.core.presenter.AdminTabPresenter;
import com.puzzlebazar.client.core.presenter.AdminUsersPresenter;
import com.puzzlebazar.client.core.presenter.LinkColumnProxy;
import com.puzzlebazar.client.core.presenter.PagePresenter;
import com.puzzlebazar.client.core.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;
import com.puzzlebazar.client.core.presenter.NewsItemPresenter;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.core.presenter.TopBarPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsAccountsPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsTabPresenter;
import com.puzzlebazar.client.core.view.AdminGeneralView;
import com.puzzlebazar.client.core.view.AdminUsersView;
import com.puzzlebazar.client.core.view.AdminTabView;
import com.puzzlebazar.client.core.view.PageView;
import com.puzzlebazar.client.core.view.LinkColumnView;
import com.puzzlebazar.client.core.view.MainPageView;
import com.puzzlebazar.client.core.view.NewsItemView;
import com.puzzlebazar.client.core.view.PuzzleView;
import com.puzzlebazar.client.core.view.SplitMainView;
import com.puzzlebazar.client.core.view.TopBarView;
import com.puzzlebazar.client.core.view.UserSettingsAccountsView;
import com.puzzlebazar.client.core.view.UserSettingsGeneralView;
import com.puzzlebazar.client.core.view.UserSettingsTabView;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.resources.Translations;
import com.puzzlebazar.client.ui.ShortMessageBox;
import com.puzzlebazar.client.ui.SimpleTabPanel;
import com.puzzlebazar.client.ui.SquareGridLayoutPanel;
import com.puzzlebazar.client.ui.SquareGridManipulatorFactory;
import com.puzzlebazar.client.ui.SquareGridManipulatorFactoryImpl;
import com.puzzlebazar.client.util.ChangeMonitor;
import com.puzzlebazar.client.util.DefaultChangeMonitor;

import com.google.inject.Singleton;

public class PuzzlebazarClientModule extends AbstractPresenterModule {

  @Override
  protected void configure() {  

    // NOTE: Commented lines are unused classes. They are commented out to make sure
    //       the GWT compiler does not include them.
    
    // Singletons
    bind(Resources.class).in(Singleton.class);
    bind(Translations.class).in(Singleton.class);
    bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
    bind(PlaceManager.class).to(PuzzlebazarPlaceManager.class).in(Singleton.class);
    bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
    bind(CurrentUser.class).asEagerSingleton();
    bind(RootProxy.class).asEagerSingleton();
    bind(ProxyFailureHandler.class).to(FailureHandlerAlert.class).in(Singleton.class);
    bind(SquareGridManipulatorFactory.class).to(SquareGridManipulatorFactoryImpl.class).in(Singleton.class);
    
    // Non-singletons
    bind(ChangeMonitor.class).to(DefaultChangeMonitor.class);
    
    // Static injection for classes that don't participate in dependency injection.
    // Right now this includes:
    //  - Widget classes instantiated in UIBinder.
    //  - Classes often instantiated as anonymous classes.    
//    requestStaticInjection(RoundTab.class);
    requestStaticInjection(SimpleTabPanel.class);
    requestStaticInjection(ShortMessageBox.class);
    requestStaticInjection(ActionCallback.class);
    
    // Widgets
    bind( SquareGridLayoutPanel.class );
    
    // Presenter widget
    bindPresenterWidget(NewsItemPresenter.class, NewsItemPresenter.MyView.class, NewsItemView.class);
    bindPresenterWidget(TopBarPresenter.class, TopBarPresenter.MyView.class, TopBarView.class );

    // Presenter bundles
    bind(TabbedPresenterBundle.class).in(Singleton.class);

    // Presenters
    bind(ProxyRaw.class).annotatedWith(DefaultPlace.class).to(MainPagePresenter.MyProxy.class);
    bindPresenter(PagePresenter.class,PagePresenter.MyView.class, PageView.class, PagePresenter.MyProxy.class);
    bindPresenter(SplitMainPresenter.class, SplitMainPresenter.MyView.class, SplitMainView.class, SplitMainPresenter.MyProxy.class );
    bindPresenter(LinkColumnPresenter.class, LinkColumnPresenter.MyView.class, LinkColumnView.class, LinkColumnPresenter.MyProxy.class, LinkColumnProxy.class );
    bindPresenter(UserSettingsTabPresenter.class, UserSettingsTabPresenter.MyView.class, UserSettingsTabView.class, UserSettingsTabPresenter.MyProxy.class );
    bindPresenter(UserSettingsGeneralPresenter.class, UserSettingsGeneralPresenter.MyView.class, UserSettingsGeneralView.class, UserSettingsGeneralPresenter.MyProxy.class);
    bindPresenter(UserSettingsAccountsPresenter.class, UserSettingsAccountsPresenter.MyView.class, UserSettingsAccountsView.class, UserSettingsAccountsPresenter.MyProxy.class);
    bindPresenter(AdminTabPresenter.class, AdminTabPresenter.MyView.class, AdminTabView.class, AdminTabPresenter.MyProxy.class );
    bindPresenter(AdminGeneralPresenter.class, AdminGeneralPresenter.MyView.class, AdminGeneralView.class, AdminGeneralPresenter.MyProxy.class);
    bindPresenter(AdminUsersPresenter.class, AdminUsersPresenter.MyView.class, AdminUsersView.class, AdminUsersPresenter.MyProxy.class);
    bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class, MainPageView.class, MainPagePresenter.MyProxy.class);
    bindPresenter(PuzzlePresenter.class, PuzzlePresenter.MyView.class, PuzzleView.class, PuzzlePresenter.MyProxy.class);

    
  }

}
