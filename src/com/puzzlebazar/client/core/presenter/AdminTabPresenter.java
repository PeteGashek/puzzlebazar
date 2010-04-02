package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.RequestTabsHandler;
import com.philbeaudoin.gwtp.mvp.client.TabPanel;
import com.philbeaudoin.gwtp.mvp.client.TabContainerPresenterImpl;
import com.philbeaudoin.gwtp.mvp.client.proxy.Proxy;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentHandler;
import com.philbeaudoin.gwtp.mvp.client.annotations.ContentSlot;
import com.philbeaudoin.gwtp.mvp.client.annotations.ProxyCodeSplitBundle;
import com.philbeaudoin.gwtp.mvp.client.annotations.RequestTabs;


/**
 * This is a tabbed presenter that will contain the different tabs for administration page. 
 * 
 * @author Philippe Beaudoin
 */
public class AdminTabPresenter extends TabContainerPresenterImpl<AdminTabPresenter.MyView,AdminTabPresenter.MyProxy> {

  @ContentSlot
  public static final Type<RevealContentHandler<?>> TYPE_RevealTabContent = new Type<RevealContentHandler<?>>();

  @RequestTabs
  public static final Type<RequestTabsHandler> TYPE_RequestTabs = new Type<RequestTabsHandler>();
  
  public interface MyView extends TabPanel, View {}

  @ProxyCodeSplitBundle(
      bundleClass = TabbedPresenterBundle.class, 
      id = TabbedPresenterBundle.ID_AdminTabPresenter )
  public interface MyProxy extends Proxy<AdminTabPresenter> {}
  
  @Inject
  public AdminTabPresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy ) {
    super(eventBus, view, proxy, TYPE_RevealTabContent, TYPE_RequestTabs );   
  }  

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(eventBus, SplitMainPresenter.TYPE_RevealCenterContent, this);
  }

}
