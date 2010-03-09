package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxy;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContainerProxy;

public abstract class TabContainerPresenterImpl<D extends Display & TabPanel, Proxy_ extends TabContainerProxy<?>> 
extends PresenterImpl<D, Proxy_> implements TabContainerPresenter  {

  private final Type<RequestTabsHandler> requestTabsEventType;

  private PresenterWidget tabContent = null;

  public TabContainerPresenterImpl(
      final EventBus eventBus, 
      final D display, 
      final Proxy_ proxy, 
      final Type<RequestTabsHandler> requestTabsEventType ) {
    super(eventBus, display, proxy);
    this.requestTabsEventType = requestTabsEventType;
  }

  @Override 
  public void onHide() {
    super.onHide();
    hideTabContent();
  }

  @Override
  public Tab addTab( final TabContentProxy<?> tabProxy ) {
    return getDisplay().addTab( tabProxy.getText(), tabProxy.getHistoryToken(), tabProxy.getPriority() );
  }

  @Override
  public void setTabContent(Presenter content) {
    if( tabContent != content ) {
      hideTabContent();
      tabContent = content;
      getDisplay().setTabContent( content.getWidget() );
      if( content.getProxy() instanceof TabContentProxy<?> ) {
        Tab tab = ((TabContentProxy<?>)content.getProxy()).getTab();
        getDisplay().setActiveTab( tab );
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

    // The tabs are added indirectly in onBind(), so we clear them now.
    getDisplay().removeTabs();
  }


  /**
   * Let child presenter know that it's about to be hidden.
   */
  private void hideTabContent() {
    if( tabContent != null )
      tabContent.onHide();    
  }
}
