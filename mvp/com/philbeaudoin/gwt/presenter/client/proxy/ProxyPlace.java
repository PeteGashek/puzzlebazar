package com.philbeaudoin.gwt.presenter.client.proxy;

public interface ProxyPlace extends Proxy, Place {

  /**
   * Requests the presenter to reveal itself on screen.
   * Upon being revealed presenters will ask to be inserted within 
   * their parent presenters by firing a {@link SetContentEvent}
   * which will cause the parent to be revealed too.
   */
  public void reveal();

  /**
   * Checks if the associated presenter can be revealed.
   * <p />
   * The default implementation of this method always return
   * <code>true</code>, but subclasses should override this and
   * check to make sure the current user has the privileges
   * to see the place. Make sure the places you request in 
   * {@link PlaceManager#revealDefaultPlace()} and
   * {@link PlaceManager#revealErrorPlace(String)} can 
   * reveal themselves, otherwise your application could get into
   * an infinite loop.
   *
   * @return <code>true</code> if the presenter can be revealed, <code>false</code> otherwise.
   */
  public boolean canReveal();
}
