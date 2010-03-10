package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.View;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.RequestTabsHandler;
import com.philbeaudoin.gwt.presenter.client.TabPanel;
import com.philbeaudoin.gwt.presenter.client.TabContainerPresenterImpl;
import com.philbeaudoin.gwt.presenter.client.ViewInterface;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyInterface;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContainerProxy;
import com.puzzlebazar.client.core.proxy.SplitMainProxy;


/**
 * This is a tabbed presenter that will contain the different tabs 
 * for user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsTabPresenter extends TabContainerPresenterImpl<UserSettingsTabPresenter.MyView,UserSettingsTabPresenter.MyProxy> {

  public static Type<RequestTabsHandler> TYPE_RequestTabs = new Type<RequestTabsHandler>();
  
  @ViewInterface
  public interface MyView extends TabPanel, View {}

  @ProxyInterface
  public interface MyProxy extends TabContainerProxy<UserSettingsTabPresenter> {}
  
  @Inject
  public UserSettingsTabPresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy ) {
    super(eventBus, view, proxy, TYPE_RequestTabs );   
  }

  @Override
  protected void setContentInParent() {
    SetContentEvent.fire(eventBus, SplitMainProxy.TYPE_SetCenterContent, this);
  }

}
