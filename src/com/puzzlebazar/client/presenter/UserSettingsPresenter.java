package com.puzzlebazar.client.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.RequestTabsHandler;
import com.philbeaudoin.gwt.presenter.client.TabPanel;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.TabContainerPresenterImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContainerProxy;
import com.puzzlebazar.client.proxy.SplitMainProxy;


/**
 * This is a tabbed presenter that will contain the different tabs for user settings 
 * 
 * @author beaudoin
 */
public class UserSettingsPresenter extends TabContainerPresenterImpl<UserSettingsPresenter.Display,UserSettingsPresenter.Proxy> {

  public static Type<RequestTabsHandler> TYPE_RequestTabs = new Type<RequestTabsHandler>();
  
  public interface Display extends PresenterDisplay, TabPanel {
  }

  public interface Proxy extends TabContainerProxy {}
  
  @Inject
  public UserSettingsPresenter(final EventBus eventBus, final Display display, 
      final Proxy proxy ) {
    super(eventBus, display, proxy, SplitMainProxy.TYPE_SetCenterContent, TYPE_RequestTabs );   
  }  
  

}
