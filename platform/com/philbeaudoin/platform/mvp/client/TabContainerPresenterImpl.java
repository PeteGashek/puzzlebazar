package com.philbeaudoin.platform.mvp.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxy;
import com.philbeaudoin.platform.mvp.client.proxy.TabContainerProxy;

public abstract class TabContainerPresenterImpl<V extends View & TabPanel, Proxy_ extends TabContainerProxy<?>> 
extends PresenterImpl<V, Proxy_> implements TabContainerPresenter  {

  private final Type<RequestTabsHandler> requestTabsEventType;

  private Presenter tabContent = null;

  public TabContainerPresenterImpl(
      final EventBus eventBus, 
      final V view, 
      final Proxy_ proxy, 
      final Type<RequestTabsHandler> requestTabsEventType ) {
    super(eventBus, view, proxy);
    this.requestTabsEventType = requestTabsEventType;
  }
  
  @Override
  protected void revealChildren() {
    super.revealChildren();
    if( tabContent != null )
      tabContent.reveal();    
  }

  @Override
  protected void notifyHideChildren() {
    super.notifyHideChildren();
    if( tabContent != null )
      tabContent.notifyHide();
  }
  
  @Override
  public Tab addTab( final TabContentProxy<?> tabProxy ) {
    return getView().addTab( tabProxy.getText(), tabProxy.getHistoryToken(), tabProxy.getPriority() );
  }

  @Override
  public void setTabContent(Presenter content) {
    if( tabContent != content ) {
      if( tabContent != null )
        tabContent.notifyHide();    
      tabContent = content;
      getView().setTabContent( content.getWidget() );
      if( content.getProxy() instanceof TabContentProxy<?> ) {
        Tab tab = ((TabContentProxy<?>)content.getProxy()).getTab();
        getView().setActiveTab( tab );
      }
    }
  }

  @Override
  protected void onBind() {
    super.onBind();

    // The following call will trigger a series of call to addTab, so
    // we should make sure we clear all the tabs when unbinding.
    eventBus.fireEvent( new RequestTabsEvent(requestTabsEventType, this) );
  }

  @Override
  protected void onUnbind() {  
    super.onUnbind();

    // The tabs are added indirectly in onBind() via the RequestTabsEvent, so we clear them now.
    getView().removeTabs();
  }

}
