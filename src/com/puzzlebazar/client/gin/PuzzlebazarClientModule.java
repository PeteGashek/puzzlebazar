package com.puzzlebazar.client.gin;

import com.philbeaudoin.gwt.presenter.client.DefaultEventBus;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.gin.AbstractPresenterModule;
import com.philbeaudoin.gwt.presenter.client.place.ParameterTokenFormatter;
import com.philbeaudoin.gwt.presenter.client.place.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.place.TokenFormatter;

import com.puzzlebazar.client.gin.annotations.DefaultMainPresenter;
import com.puzzlebazar.client.place.PuzzlebazarPlaceManager;
import com.puzzlebazar.client.place.UserSettingsDetailsPlace;
import com.puzzlebazar.client.place.UserSettingsMainPlace;
import com.puzzlebazar.client.presenter.AppPresenter;
import com.puzzlebazar.client.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.presenter.SplitMainPresenter;
import com.puzzlebazar.client.presenter.TopBarPresenter;
import com.puzzlebazar.client.presenter.UserSettingsDetailsPresenter;
import com.puzzlebazar.client.presenter.UserSettingsMainPresenter;
import com.puzzlebazar.client.presenter.UserSettingsPresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.resources.Translations;
import com.puzzlebazar.client.ui.Tab;
import com.puzzlebazar.client.view.AppView;
import com.puzzlebazar.client.view.LinkColumnView;
import com.puzzlebazar.client.view.SplitMainView;
import com.puzzlebazar.client.view.TopBarView;
import com.puzzlebazar.client.view.UserSettingsDetailsView;
import com.puzzlebazar.client.view.UserSettingsMainView;
import com.puzzlebazar.client.view.UserSettingsView;

import com.google.inject.Singleton;

public class PuzzlebazarClientModule extends AbstractPresenterModule {

  @Override
  protected void configure() {  

    bind(Resources.class).in(Singleton.class);
    bind(Translations.class).in(Singleton.class);
    bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
    bind(PlaceManager.class).to(PuzzlebazarPlaceManager.class);
    bind(TokenFormatter.class).to(ParameterTokenFormatter.class);

    // Places
    bind(UserSettingsMainPlace.class).asEagerSingleton();
    bind(UserSettingsDetailsPlace.class).asEagerSingleton();


    // Presenters
    bindPresenter(AppPresenter.class,AppPresenter.Display.class, AppView.class, AppPresenter.Wrapper.class);
    bind(AppPresenter.MainSlot.class).in(Singleton.class);
    bind(Presenter.class).annotatedWith(DefaultMainPresenter.class).to(SplitMainPresenter.class);
    bindPresenter(SplitMainPresenter.class, SplitMainPresenter.Display.class, SplitMainView.class, SplitMainPresenter.Wrapper.class );
    bind( SplitMainPresenter.SideBarSlot.class).in(Singleton.class);
    bind( SplitMainPresenter.CenterSlot.class).in(Singleton.class);
    bindPresenter(LinkColumnPresenter.class,LinkColumnPresenter.Display.class, LinkColumnView.class, LinkColumnPresenter.Wrapper.class );
    bindPresenter(TopBarPresenter.class,TopBarPresenter.Display.class, TopBarView.class, TopBarPresenter.Wrapper.class );
    bindPresenter(UserSettingsPresenter.class,UserSettingsPresenter.Display.class, UserSettingsView.class, UserSettingsPresenter.Wrapper.class );
    bind( UserSettingsPresenter.MainSlot.class).in(Singleton.class);
    bindPresenter(UserSettingsMainPresenter.class,UserSettingsMainPresenter.Display.class, UserSettingsMainView.class, UserSettingsMainPresenter.Wrapper.class);
    bindPresenter(UserSettingsDetailsPresenter.class,UserSettingsDetailsPresenter.Display.class, UserSettingsDetailsView.class, UserSettingsDetailsPresenter.Wrapper.class);

    // Static injectors for UIBinder instantiable widgets
    requestStaticInjection( Tab.class );

  }
}
