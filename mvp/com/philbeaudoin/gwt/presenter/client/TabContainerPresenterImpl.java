package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxy;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentHandler;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContainerProxy;

public abstract class TabContainerPresenterImpl<D extends PresenterDisplay & TabPanel, P extends TabContainerProxy> 
extends PresenterImpl<D, P> implements TabContainerPresenter  {

  private final Type<RequestTabsHandler> requestTabsEventType;

  private Presenter tabContent = null;

  public TabContainerPresenterImpl(
      final EventBus eventBus, 
      final Provider<D> display, 
      final P proxy, 
      final Type<SetContentHandler<?>> setContentInParentEventType,
      final Type<RequestTabsHandler> requestTabsEventType ) {
    super(eventBus, display, proxy, setContentInParentEventType);
    this.requestTabsEventType = requestTabsEventType;
  }

  @Override
  public Tab addTab( final TabContentProxy tabProxy ) {
    return getDisplay().addTab( tabProxy.getText(), tabProxy.getNameToken(), tabProxy.getPriority() );
  }

  @Override
  public void setTabContent(Presenter content) {
    if( tabContent != content ) {
      tabContent = content;
      getDisplay().setTabContent( content.getWidget() );
      if( content.getProxy() instanceof TabContentProxyImpl<?> ) {
        Tab tab = ((TabContentProxyImpl<?>)content.getProxy()).getTab();
        getDisplay().setActiveTab( tab );
      }
    }
  }

  @Override
  public void onBind() {
    super.onBind();
    
    // The following call will trigger a series of call to addTab, so
    // we should make sure we clear all the tabs when unbinding.
    eventBus.fireEvent( new RequestTabsEvent(requestTabsEventType, this) );
  }

  @Override
  public void onUnbind() {  
    super.onUnbind();

    // The tabs are added indirectly in onBind(), so we clear them now.
    getDisplay().removeTabs();
  }
  
}
