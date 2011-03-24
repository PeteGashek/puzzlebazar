/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.puzzlebazar.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;
import com.puzzlebazar.client.ActionCallback;
import com.puzzlebazar.client.AdminGatekeeper;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.FailureHandlerAlert;
import com.puzzlebazar.client.LoggedInGatekeeper;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.PuzzlebazarPlaceManager;
import com.puzzlebazar.client.core.presenter.AdminGeneralPresenter;
import com.puzzlebazar.client.core.presenter.AdminTabPresenter;
import com.puzzlebazar.client.core.presenter.AdminUsersPresenter;
import com.puzzlebazar.client.core.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;
import com.puzzlebazar.client.core.presenter.NewsItemPresenter;
import com.puzzlebazar.client.core.presenter.PagePresenter;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.core.presenter.TopBarPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsAccountsPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsTabPresenter;
import com.puzzlebazar.client.core.view.AdminGeneralView;
import com.puzzlebazar.client.core.view.AdminTabView;
import com.puzzlebazar.client.core.view.AdminUsersView;
import com.puzzlebazar.client.core.view.LinkColumnView;
import com.puzzlebazar.client.core.view.MainPageView;
import com.puzzlebazar.client.core.view.NewsItemView;
import com.puzzlebazar.client.core.view.PageView;
import com.puzzlebazar.client.core.view.PuzzleView;
import com.puzzlebazar.client.core.view.SplitMainView;
import com.puzzlebazar.client.core.view.TopBarView;
import com.puzzlebazar.client.core.view.UserSettingsAccountsView;
import com.puzzlebazar.client.core.view.UserSettingsGeneralView;
import com.puzzlebazar.client.core.view.UserSettingsTabView;
import com.puzzlebazar.client.puzzle.heyawake.presenter.HeyawakePresenter;
import com.puzzlebazar.client.puzzle.heyawake.view.HeyawakeView;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.resources.Translations;
import com.puzzlebazar.client.ui.ShortMessageBox;
import com.puzzlebazar.client.ui.SimpleTabPanel;
import com.puzzlebazar.client.ui.SquareGridLayoutPanel;
import com.puzzlebazar.client.ui.SquareGridManipulator;
import com.puzzlebazar.client.ui.SquareGridManipulatorImpl;
import com.puzzlebazar.client.util.ChangeMonitor;
import com.puzzlebazar.client.util.DefaultChangeMonitor;
import com.puzzlebazar.shared.Constants;

/**
 * @author Philippe Beaudoin
 */
public class PuzzlebazarClientModule extends AbstractPresenterModule {

  @Override
  protected void configure() {

    // NOTE: Commented lines are unused classes. They are commented out to make sure
    //       the GWT compiler does not include them.
    bindConstant().annotatedWith(SecurityCookie.class).to(Constants.securityCookieName);

    // Singletons
    bind(Resources.class).in(Singleton.class);
    bind(Translations.class).in(Singleton.class);
    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
    bind(PlaceManager.class).to(PuzzlebazarPlaceManager.class).in(Singleton.class);
    bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
    bind(CurrentUser.class).asEagerSingleton();
    bind(RootPresenter.class).asEagerSingleton();
    bind(ProxyFailureHandler.class).to(FailureHandlerAlert.class).in(Singleton.class);
    bind(SquareGridManipulator.Factory.class).to(SquareGridManipulatorImpl.FactoryImpl.class).in(Singleton.class);
    bind(LoggedInGatekeeper.class).in(Singleton.class);
    bind(AdminGatekeeper.class).in(Singleton.class);

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
    bind(SquareGridLayoutPanel.class);

    // Presenter widget
    bindPresenterWidget(NewsItemPresenter.class, NewsItemPresenter.MyView.class, NewsItemView.class);
    bindSingletonPresenterWidget(TopBarPresenter.class, TopBarPresenter.MyView.class, TopBarView.class);
    bindPresenterWidgetFactory(
        HeyawakePresenter.Factory.class,
        HeyawakePresenter.FactoryImpl.class,
        HeyawakePresenter.ViewFactory.class,
        HeyawakeView.FactoryImpl.class);

    // Presenter bundles
    bind(TabbedPresenterBundle.class).in(Singleton.class);

    // Constants
    bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.mainPage);
    bindConstant().annotatedWith(RetryDelay.class).to(500);
    bindConstant().annotatedWith(MaxRetries.class).to(4);

    // Presenters
    bindPresenter(PagePresenter.class,PagePresenter.MyView.class, PageView.class, PagePresenter.MyProxy.class);
    bindPresenter(SplitMainPresenter.class, SplitMainPresenter.MyView.class, SplitMainView.class, SplitMainPresenter.MyProxy.class);
//    bindPresenter(LinkColumnPresenter.class, LinkColumnPresenter.MyView.class, LinkColumnView.class, LinkColumnPresenter.MyProxy.class, LinkColumnProxy.class );
    bindPresenter(LinkColumnPresenter.class, LinkColumnPresenter.MyView.class, LinkColumnView.class, LinkColumnPresenter.MyProxy.class);
    bindPresenter(UserSettingsTabPresenter.class, UserSettingsTabPresenter.MyView.class, UserSettingsTabView.class, UserSettingsTabPresenter.MyProxy.class);
    bindPresenter(UserSettingsGeneralPresenter.class, UserSettingsGeneralPresenter.MyView.class, UserSettingsGeneralView.class, UserSettingsGeneralPresenter.MyProxy.class);
    bindPresenter(UserSettingsAccountsPresenter.class, UserSettingsAccountsPresenter.MyView.class, UserSettingsAccountsView.class, UserSettingsAccountsPresenter.MyProxy.class);
    bindPresenter(AdminTabPresenter.class, AdminTabPresenter.MyView.class, AdminTabView.class, AdminTabPresenter.MyProxy.class);
    bindPresenter(AdminGeneralPresenter.class, AdminGeneralPresenter.MyView.class, AdminGeneralView.class, AdminGeneralPresenter.MyProxy.class);
    bindPresenter(AdminUsersPresenter.class, AdminUsersPresenter.MyView.class, AdminUsersView.class, AdminUsersPresenter.MyProxy.class);
    bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class, MainPageView.class, MainPagePresenter.MyProxy.class);
    bindPresenter(PuzzlePresenter.class, PuzzlePresenter.MyView.class, PuzzleView.class, PuzzlePresenter.MyProxy.class);
  }

}
