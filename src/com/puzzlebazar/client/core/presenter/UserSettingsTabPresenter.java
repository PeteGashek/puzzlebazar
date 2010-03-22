package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.RequestTabsHandler;
import com.philbeaudoin.platform.mvp.client.TabPanel;
import com.philbeaudoin.platform.mvp.client.TabContainerPresenterImpl;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentHandler;


/**
 * This is a tabbed presenter that will contain the different tabs 
 * for user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsTabPresenter extends TabContainerPresenterImpl<UserSettingsTabPresenter.MyView,UserSettingsTabPresenter.MyProxy> {

  public static final Type<RevealContentHandler<?>> TYPE_RevealTabContent  = new Type<RevealContentHandler<?>>();
  public static final Type<RequestTabsHandler> TYPE_RequestTabs = new Type<RequestTabsHandler>();
  
  public interface MyView extends TabPanel, View {}

  public interface MyProxy extends Proxy<UserSettingsTabPresenter> {}
  
  @Inject
  public UserSettingsTabPresenter(
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
