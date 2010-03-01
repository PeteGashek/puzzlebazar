package com.philbeaudoin.gwt.presenter.client.proxy;

public interface Place {

  public boolean equals(Object o);

  public int hashCode();

  public String toString();
  
  /**
   * Returns the unique name for this place. Uniqueness is not enforced -
   * creators must ensure that the name is unique or there will be potential
   * issues with multiple places responding to the same History token.
   *
   * @return The place ID.
   */
  public String getHistoryToken();

  /**
   * This method is checked before calling
   * {@link #handleRequest(PlaceRequest)}.
   *
   * @param request The request to check.
   * @return <code>true</code> if the ID matches this place's name.
   */
  public boolean matchesRequest(PlaceRequest request);

  /**
   * Returns a new request for this place in its current state. This method
   * calls {@link #prepareRequest(PlaceRequest)} before returning.
   *
   * @return The new {@link PlaceRequest}.
   */
  public PlaceRequest createRequest();

}