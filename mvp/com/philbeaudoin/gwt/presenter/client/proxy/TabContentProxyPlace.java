package com.philbeaudoin.gwt.presenter.client.proxy;

import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.Tab;

/**
 * A useful mixing class to define a {@link TabContentProxy} that is also
 * a {@link Place}.
 * 
 * @author Philippe Beaudoin
 */
public class TabContentProxyPlace<P extends Presenter> 
extends ProxyPlaceAbstract<P, TabContentProxy<P>>
implements TabContentProxy<P> {

  public TabContentProxyPlace(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final TabContentProxy<P> proxy,
      final Place place) {
    super(eventBus, placeManager, proxy, place);
  }
  
  @Override
  public float getPriority() {
    return proxy.getPriority();
  }

  @Override
  public Tab getTab() {
    return proxy.getTab();
  }

  @Override
  public String getText() {
    return proxy.getText();
  }

  @Override
  public String getHistoryToken() {
    return getNameToken();
  }

}
