package com.philbeaudoin.gwt.presenter.client.proxy;

import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;

/**
 * A useful mixing class to define a {@link Proxy} that is also
 * a {@link Place}. See {@link ProxyPlaceAbstract} for more details.
 * 
 * @param <P> Type of the associated {@link Presenter}.
 *
 * @author David Peterson
 * @author Philippe Beaudoin
 */
public class ProxyPlace<P extends Presenter> 
extends ProxyPlaceAbstract<P, Proxy<P>> {

  public ProxyPlace(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final Proxy<P> proxy, 
      final Place place) {
    super(eventBus, placeManager, proxy, place);
  }

}
