package com.philbeaudoin.gwt.presenter.client.place;

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

  private final boolean fromHistory;

  public PlaceRequestEvent( PlaceRequest request ) {
    this( request, false );
  }

  PlaceRequestEvent( PlaceRequest request, boolean fromHistory ) {
    this.request = request;
    this.fromHistory = fromHistory;
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

  boolean isFromHistory() {
    return fromHistory;
  }

  /**
   * Fires a {@link PlaceRequestEvent} into the {@link EventBus}, specifying that it
   * does not come from a modification in the History.
   *
   * @param eventBus  The event bus.
   * @param request   The request.
   */
  public static void fire( EventBus eventBus, PlaceRequest request ) {
    fire( eventBus, request, false );
  }

  /**
   * Fires a {@link PlaceRequestEvent} into the {@link EventBus}, specifying that it
   * does not come from a modification in the History.
   *
   * @param eventBus    The event bus.
   * @param request     The request.
   * @param fromHistory true if the request fomes from a modification in the History, false otherwise. 
   */
  public static void fire( EventBus eventBus, PlaceRequest request, boolean fromHistory ) {
    eventBus.fireEvent( new PlaceRequestEvent( request, fromHistory ) );
  }
}
