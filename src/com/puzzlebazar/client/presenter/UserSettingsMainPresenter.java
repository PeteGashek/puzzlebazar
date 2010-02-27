package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.proxy.PresenterProxy;
import com.puzzlebazar.client.proxy.UserSettingsProxy;

/**
 * This is the main presenter of the user settings tab presenter
 * 
 * @author beaudoin
 */
public class UserSettingsMainPresenter 
extends PresenterImpl<UserSettingsMainPresenter.Display, UserSettingsMainPresenter.Proxy> {

  public interface Display extends PresenterDisplay { }

  public interface Proxy extends PresenterProxy {}
  
  @Inject
  public UserSettingsMainPresenter(final EventBus eventBus, 
      final Display display, 
      final Proxy proxy) {
    super(eventBus, display, proxy, UserSettingsProxy.TYPE_SetTabContent );
  }

}
