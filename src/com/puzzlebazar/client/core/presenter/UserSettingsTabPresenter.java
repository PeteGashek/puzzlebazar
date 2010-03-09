package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.RequestTabsHandler;
import com.philbeaudoin.gwt.presenter.client.TabPanel;
import com.philbeaudoin.gwt.presenter.client.TabContainerPresenterImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContainerProxy;
import com.puzzlebazar.client.core.proxy.SplitMainProxy;


/**
 * This is a tabbed presenter that will contain the different tabs 
 * for user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsTabPresenter extends TabContainerPresenterImpl<UserSettingsTabPresenter.MyDisplay,UserSettingsTabPresenter.MyProxy> {

  public static Type<RequestTabsHandler> TYPE_RequestTabs = new Type<RequestTabsHandler>();
  
  public interface MyDisplay extends TabPanel, Display {}

  public interface MyProxy extends TabContainerProxy<UserSettingsTabPresenter> {}
  
  @Inject
  public UserSettingsTabPresenter(
      final EventBus eventBus, 
      final MyDisplay display, 
      final MyProxy proxy ) {
    super(eventBus, display, proxy, TYPE_RequestTabs );   
  }

  @Override
  protected void setContentInParent() {
    SetContentEvent.fire(eventBus, SplitMainProxy.TYPE_SetCenterContent, this);
  }

}
