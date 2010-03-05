package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyPlace;
import com.puzzlebazar.client.core.proxy.UserSettingsProxy;

/**
 * This is the presenter of the general tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsGeneralPresenter 
extends PresenterImpl<UserSettingsGeneralPresenter.Display, UserSettingsGeneralPresenter.Proxy> {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display { }

  public interface Proxy extends ProxyPlace {}

  @Inject
  public UserSettingsGeneralPresenter(final EventBus eventBus, 
      final Provider<Display> display, 
      final Proxy proxy ) {
    super(eventBus, display, proxy, UserSettingsProxy.TYPE_SetTabContent );
  }
}
