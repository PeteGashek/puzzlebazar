package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.gwt.presenter.client.EventBus;

/**
 * 
 * This event is fired whenever a  new place is requested, either by 
 * history navigation or directly 
 * 
 * @author David Peterson
 *
 */
public class PlaceRequestEvent extends GwtEvent<PlaceRequestHandler> {

  private static Type<PlaceRequestHandler> TYPE;

  public static Type<PlaceRequestHandler> getType() {
    if ( TYPE == null )
      TYPE = new Type<PlaceRequestHandler>();
    return TYPE;
  }

  private final PlaceRequest request;

  public PlaceRequestEvent( PlaceRequest request ) {
    this.request = request;
  }

  @Override
  protected void dispatch( PlaceRequestHandler handler ) {
    handler.onPlaceRequest( this );
  }

  @Override
  public Type<PlaceRequestHandler> getAssociatedType() {
    return getType();
  }

  public PlaceRequest getRequest() {
    return request;
  }


  /**
   * Fires a {@link PlaceRequestEvent} into the {@link EventBus}, specifying that it
   * does not come from a modification in the History.
   *
   * @param eventBus  The event bus.
   * @param request   The request.
   */
  public static void fire( EventBus eventBus, PlaceRequest request ) {
    eventBus.fireEvent( new PlaceRequestEvent( request ) );
  }
}
