package com.puzzlebazar.client.core.presenter;

import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.Place;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxy;
import com.puzzlebazar.client.core.proxy.UserSettingsTabProxy;
import com.puzzlebazar.client.utils.ChangeMonitor;

/**
 * This is the presenter of the general tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsGeneralPresenter 
extends PresenterImpl<UserSettingsGeneralPresenter.MyDisplay, UserSettingsGeneralPresenter.MyProxy> {

  public interface MyDisplay extends Display {
    HasText getNickname();
    HasText getRealName(); 
  }

  public interface MyProxy extends TabContentProxy<UserSettingsGeneralPresenter>, Place {}

  private final ChangeMonitor changeMonitor;

  @Inject
  public UserSettingsGeneralPresenter(
      final EventBus eventBus,
      final MyDisplay display, 
      final MyProxy proxy,
      final ChangeMonitor changeMonitor ) {
    super(eventBus, display, proxy );
    this.changeMonitor = changeMonitor;
  }

  @Override
  protected void onBind() {
    super.onBind();
    changeMonitor.monitorWidget( display.getNickname() );
    changeMonitor.monitorWidget( display.getRealName() );
  }
  
  @Override
  protected void setContentInParent() {
    SetContentEvent.fire(eventBus, UserSettingsTabProxy.TYPE_SetTabContent, this);
  }
}
