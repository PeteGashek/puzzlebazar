package com.philbeaudoin.gwt.presenter.client.proxy;

/**
 * Place managers work as an intermediary between the GWT {@link com.google.gwt.user.client.History} API
 * and {@link ProxyPlaceImpl}s. It sets up event listener relationships to synchronize them.
 *
 * @author David Peterson
 */
public interface PlaceManager {

  /**
   * Fires an event for the current place.
   *
   * @return <code>false</code> if there was no current place to fire.
   */
  public boolean fireCurrentPlace();

  /**
   * Called whenever the current place has changed in a way that requires history parameters to be 
   * modified. 
   *
   * @param place The place that has changed.
   */
  void onPlaceChanged( Place place );

  /**
   * Called whenever a new place has been revealed.
   *
   * @param place The place that has been revealed.
   */
  void onPlaceRevealed( Place place );
  
}
