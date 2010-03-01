package com.philbeaudoin.gwt.presenter.client.proxy;

public interface ProxyPlace extends PresenterProxy, Place {

  /**
   * Requests the presenter to reveal itself on screen.
   * Upon being revealed presenters will ask to be inserted within 
   * their parent presenters by firing a {@link SetContentEvent}
   * which will cause the parent to be revealed too.
   */
  public void reveal();

}
