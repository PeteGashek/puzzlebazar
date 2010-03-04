package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.RequestTabsEvent;
import com.philbeaudoin.gwt.presenter.client.RequestTabsHandler;
import com.philbeaudoin.gwt.presenter.client.Tab;
import com.philbeaudoin.gwt.presenter.client.TabContainerPresenter;

public abstract class TabContentProxyImpl<P extends Presenter> 
extends ProxyPlaceImpl<P> implements TabContentProxy {

  private final Type<RequestTabsHandler> requestTabsEventType;
  private Tab tab = null;

  /**
   * Creates a {@link Proxy} for a {@link Presenter} that 
   * is meant to be contained within at {@link TabContainerPresenter}.
   * As such, these proxy hold a string that can be displayed on the 
   * tab. 
   * 
   * @param eventBus The {@link EventBus}.
   * @param placeManager The {@link PlaceManager}.
   * @param presenter A {@link Provider} for the {@link Presenter} of which this class is a proxy. 
   * @param requestTabsEventType The {@Type} of the {@link RequestTabsEvent} this proxy will 
   *        listen to. Whenever this event is received, this proxy will add itself as a tab
   *        to the {@link TabContainerPresenter} specified in the event.
   */
  public TabContentProxyImpl(final EventBus eventBus, 
      final PlaceManager placeManager, 
      final CallbackProvider<P> presenter, 
      final Type<RequestTabsHandler> requestTabsEventType ) {
    super(eventBus, placeManager, presenter);
    
    this.requestTabsEventType = requestTabsEventType;
  }

  @Override
  public Tab getTab() {
    return tab;
  }
  
  @Override
  public void onBind() {
    super.onBind();
    
    registerHandler( eventBus.addHandler(requestTabsEventType, new RequestTabsHandler(){
      @Override
      public void onRequestTabs(RequestTabsEvent event) {
        tab = event.getTabContainer().addTab( TabContentProxyImpl.this );
      }
    } ) );
  }
  
  
}
