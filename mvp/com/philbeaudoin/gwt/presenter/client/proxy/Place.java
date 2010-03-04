package com.philbeaudoin.gwt.presenter.client.proxy;

public interface Place {

  public boolean equals(Object o);

  public int hashCode();

  public String toString();
  
  /**
   * The name token is the first part of the history token, before the 
   * parameters. It is meant to be a unique identifier of a place.
   * An exception will be thrown if two places are registered with the
   * same name token.
   *
   * @return The name token for this place.
   */
  public String getNameToken();

  /**
   * This method is checked before calling
   * {@link #handleRequest(PlaceRequest)}.
   *
   * @param request The request to check.
   * @return <code>true</code> if the ID matches this place's name.
   */
  public boolean matchesRequest(PlaceRequest request);

}