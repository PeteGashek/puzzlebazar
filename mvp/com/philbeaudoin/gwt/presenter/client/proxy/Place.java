package com.philbeaudoin.gwt.presenter.client.proxy;

public interface Place {

  @Override
  public boolean equals(Object o);

  @Override
  public int hashCode();

  @Override
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
   * Makes sure the method matches the passed request.
   *
   * @param request The request to check.
   * @return <code>true</code> if the ID matches this place's name.
   */
  public boolean matchesRequest(PlaceRequest request);

}