package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyPlace;
import com.puzzlebazar.client.core.proxy.AdminProxy;

/**
 * This is the main presenter of the user settings tab presenter
 * 
 * @author beaudoin
 */
public class AdminGeneralPresenter 
extends PresenterImpl<AdminGeneralPresenter.Display, AdminGeneralPresenter.Proxy> {

  public interface Display extends PresenterDisplay { }

  public interface Proxy extends ProxyPlace {}

  @Inject
  public AdminGeneralPresenter(final EventBus eventBus, 
      final Provider<Display> display, 
      final Proxy proxy ) {
    super(eventBus, display, proxy, AdminProxy.TYPE_SetTabContent );
  }
}
