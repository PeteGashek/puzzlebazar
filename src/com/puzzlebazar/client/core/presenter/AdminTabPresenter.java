package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.Display;
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
public class AdminTabPresenter extends TabContainerPresenterImpl<AdminTabPresenter.MyDisplay,AdminTabPresenter.MyProxy> {

  public static Type<RequestTabsHandler> TYPE_RequestTabs = new Type<RequestTabsHandler>();
  
  public interface MyDisplay extends TabPanel, Display {}

  public interface MyProxy extends TabContainerProxy<AdminTabPresenter> {}
  
  @Inject
  public AdminTabPresenter(
      final EventBus eventBus, 
      final Provider<MyDisplay> display, 
      final MyProxy proxy ) {
    super(eventBus, display, proxy, SplitMainProxy.TYPE_SetCenterContent, TYPE_RequestTabs );   
  }  
  

}
