package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.RequestTabsHandler;
import com.philbeaudoin.platform.mvp.client.TabPanel;
import com.philbeaudoin.platform.mvp.client.TabContainerPresenterImpl;
import com.philbeaudoin.platform.mvp.client.proxy.SetContentEvent;
import com.philbeaudoin.platform.mvp.client.proxy.TabContainerProxy;
import com.puzzlebazar.client.core.proxy.SplitMainProxy;


/**
 * This is a tabbed presenter that will contain the different tabs for administration page. 
 * 
 * @author Philippe Beaudoin
 */
public class AdminTabPresenter extends TabContainerPresenterImpl<AdminTabPresenter.MyView,AdminTabPresenter.MyProxy> {

  public static Type<RequestTabsHandler> TYPE_RequestTabs = new Type<RequestTabsHandler>();
  
  public interface MyView extends TabPanel, View {}

  public interface MyProxy extends TabContainerProxy<AdminTabPresenter> {}
  
  @Inject
  public AdminTabPresenter(
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
