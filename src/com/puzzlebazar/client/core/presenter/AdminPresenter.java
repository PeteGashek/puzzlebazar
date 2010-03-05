package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.RequestTabsHandler;
import com.philbeaudoin.gwt.presenter.client.TabPanel;
import com.philbeaudoin.gwt.presenter.client.TabContainerPresenterImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContainerProxy;
import com.puzzlebazar.client.core.proxy.SplitMainProxy;


/**
 * This is a tabbed presenter that will contain the different tabs for administration page. 
 * 
 * @author Philippe Beaudoin
 */
public class AdminPresenter extends TabContainerPresenterImpl<AdminPresenter.Display,AdminPresenter.Proxy> {

  public static Type<RequestTabsHandler> TYPE_RequestTabs = new Type<RequestTabsHandler>();
  
  public interface Display extends TabPanel, com.philbeaudoin.gwt.presenter.client.Display {
  }

  public interface Proxy extends TabContainerProxy {}
  
  @Inject
  public AdminPresenter(
      final EventBus eventBus, 
      final Provider<Display> display, 
      final Proxy proxy ) {
    super(eventBus, display, proxy, SplitMainProxy.TYPE_SetCenterContent, TYPE_RequestTabs );   
  }  
  

}
