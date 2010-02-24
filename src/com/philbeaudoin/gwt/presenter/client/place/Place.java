package com.philbeaudoin.gwt.presenter.client.place;

import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.HandlerContainer;

/**
 * A place represents a particular 'bookmark' or location inside the
 * application. A place is stateful - it may represent a location with it's
 * current settings, such as a particular ID value, or other unique indicators
 * that will allow a user to track back to that location later, either via a
 * browser bookmark, or by clicking the 'back' button.
 * <p/>
 * However, there may be more than one instance of concrete Place classes, so
 * the state should be shared between all instances of any given class. Usually
 * this is done via a shared class, such as a {@link com.philbeaudoin.gwt.presenter.client.Presenter} instance.
 *
 * @author David Peterson
 */
public abstract class Place extends HandlerContainer {

  protected final EventBus eventBus;

  public Place( EventBus eventBus ) {
    this.eventBus = eventBus;
  }

  /**
   * Returns the unique name for this place. Uniqueness is not enforced -
   * creators must ensure that the name is unique or there will be potential
   * issues with multiple places responding to the same History token.
   *
   * @return The place ID.
   */
  public abstract String getHistoryToken();

  @Override
  public boolean equals( Object o ) {
    if ( o instanceof Place ) {
      Place place = (Place) o;
      return getHistoryToken().equals( place.getHistoryToken() );
    }
    return false;
  }

  @Override
  public int hashCode() {
    return 17 * getHistoryToken().hashCode();
  }

  @Override
  public String toString() {
    return getHistoryToken();
  }

  /**
   * This method is called when a matching request is received.
   *
   * @param request The place request.
   */
  protected abstract void handleRequest( PlaceRequest request );

  /**
   * This method is checked before calling
   * {@link #handleRequest(PlaceRequest)}.
   *
   * @param request The request to check.
   * @return <code>true</code> if the ID matches this place's name.
   */
  public boolean matchesRequest( PlaceRequest request ) {
    return getHistoryToken().equals( request.getName() );
  }

  /**
   * Returns a new request for this place in its current state. This method
   * calls {@link #prepareRequest(PlaceRequest)} before returning.
   *
   * @return The new {@link PlaceRequest}.
   */
  public PlaceRequest createRequest() {
    return prepareRequest( new PlaceRequest( getHistoryToken() ) );
  }

  /**
   * Returns the request with any extra values relevant to the current place
   * initialised. If nothing needs to be done, return the request that was
   * passed in.
   * <p/>
   * <p/>
   * Typical usage:
   * <p/>
   * <pre>
   * return request.with( &quot;name&quot;, idValue ).with( &quot;name&quot;, &quot;Foo&quot; );
   * </pre>
   *
   * @param request The current request object.
   * @return The prepared request.
   */
  protected abstract PlaceRequest prepareRequest( PlaceRequest request );

  @Override
  protected void onBind() {
    registerHandler( eventBus.addHandler( PlaceRequestEvent.getType(), new PlaceRequestHandler() {
      public void onPlaceRequest( PlaceRequestEvent event ) {
        PlaceRequest request = event.getRequest();
        if ( matchesRequest( request ) ) {
          handleRequest( request );
          eventBus.fireEvent( new PlaceRevealedEvent( Place.this ) );
        }
      }
    } ) );
  }

}
