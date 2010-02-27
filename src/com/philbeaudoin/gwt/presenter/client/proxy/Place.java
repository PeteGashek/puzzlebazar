package com.philbeaudoin.gwt.presenter.client.proxy;

public interface Place {

  /**
   * Returns the unique name for this place. Uniqueness is not enforced -
   * creators must ensure that the name is unique or there will be potential
   * issues with multiple places responding to the same History token.
   *
   * @return The place ID.
   */
  public abstract String getHistoryToken();

  public abstract boolean equals(Object o);

  public abstract int hashCode();

  public abstract String toString();

  /**
   * This method is checked before calling
   * {@link #handleRequest(PlaceRequest)}.
   *
   * @param request The request to check.
   * @return <code>true</code> if the ID matches this place's name.
   */
  public abstract boolean matchesRequest(PlaceRequest request);

  /**
   * Returns a new request for this place in its current state. This method
   * calls {@link #prepareRequest(PlaceRequest)} before returning.
   *
   * @return The new {@link PlaceRequest}.
   */
  public abstract PlaceRequest createRequest();

}