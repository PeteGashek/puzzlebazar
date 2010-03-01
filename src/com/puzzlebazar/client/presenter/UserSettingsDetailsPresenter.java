package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyPlace;
import com.puzzlebazar.client.proxy.UserSettingsProxy;

/**
 * This is the details presenter of the user settings tab presenter
 * 
 * @author beaudoin
 */
public class UserSettingsDetailsPresenter extends PresenterImpl<UserSettingsDetailsPresenter.Display, UserSettingsDetailsPresenter.Proxy> {


  public interface Display extends PresenterDisplay { }

  public interface Proxy extends ProxyPlace {}
  
  @Inject
  public UserSettingsDetailsPresenter(final EventBus eventBus, final Display display,  
      final Proxy proxy ) {
    super( eventBus, display, proxy, UserSettingsProxy.TYPE_SetTabContent );
  }

}
