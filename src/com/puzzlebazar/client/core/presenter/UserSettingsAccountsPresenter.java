package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.Place;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxy;
import com.puzzlebazar.client.core.proxy.UserSettingsTabProxy;

/**
 * This is the presenter of the accounts tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsAccountsPresenter extends PresenterImpl<UserSettingsAccountsPresenter.MyDisplay, UserSettingsAccountsPresenter.MyProxy> {

  public interface MyDisplay extends Display { }

  public interface MyProxy extends TabContentProxy<UserSettingsAccountsPresenter>, Place {}

  @Inject
  public UserSettingsAccountsPresenter(
      final EventBus eventBus, 
      final Provider<MyDisplay> display,  
      final MyProxy proxy ) {
    super(
        eventBus, 
        display, 
        proxy, 
        UserSettingsTabProxy.TYPE_SetTabContent );
  }
  
}
