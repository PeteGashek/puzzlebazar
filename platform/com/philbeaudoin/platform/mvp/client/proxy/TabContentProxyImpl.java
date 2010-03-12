package com.philbeaudoin.platform.mvp.client.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Provider;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.Presenter;
import com.philbeaudoin.platform.mvp.client.RequestTabsEvent;
import com.philbeaudoin.platform.mvp.client.RequestTabsHandler;
import com.philbeaudoin.platform.mvp.client.Tab;
import com.philbeaudoin.platform.mvp.client.TabContainerPresenter;

public class TabContentProxyImpl<P extends Presenter> 
extends ProxyImpl<P> implements TabContentProxy<P> {

  private final Type<RequestTabsHandler> requestTabsEventType;
  private final float priority;
  private final String text;
  private final String historyToken;

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
   * @param requestTabsEventType The {@link Type} of the {@link RequestTabsEvent} this proxy will 
   *        listen to. Whenever this event is received, this proxy will add itself as a tab
   *        to the {@link TabContainerPresenter} specified in the event.
   */
  public TabContentProxyImpl(final EventBus eventBus, 
      final CallbackProvider<P> presenter, 
      final Type<RequestTabsHandler> requestTabsEventType,
      final float priority,
      final String text,
      final String historyToken ) {
    super(eventBus, presenter);
    
    this.requestTabsEventType = requestTabsEventType;
    this.priority = priority;
    this.text = text;
    this.historyToken = historyToken;
  }

  @Override
  public Tab getTab() {
    return tab;
  }
  
  @Override
  protected void onBind() {
    super.onBind();
    
    registerHandler( eventBus.addHandler(requestTabsEventType, new RequestTabsHandler(){
      @Override
      public void onRequestTabs(RequestTabsEvent event) {
        tab = event.getTabContainer().addTab( TabContentProxyImpl.this );
      }
    } ) );
  }

  @Override
  public float getPriority() {
    return priority;
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public String getHistoryToken() {
    return historyToken;
  }
  
}
