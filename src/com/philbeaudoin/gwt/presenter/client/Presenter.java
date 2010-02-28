package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceRequest;
import com.philbeaudoin.gwt.presenter.client.proxy.PresenterProxy;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;

public interface Presenter {

  /**
   * Returns the {@link PresenterDisplay} for the current presenter.
   *
   * @return The display.
   */
  PresenterDisplay getDisplay();

  /**
   * Returns the {@link PresenterProxy} for the current presenter.
   *
   * @return The proxy.
   */
  PresenterProxy getProxy();

  /**
   * Requests the presenter to reveal itself on screen.
   * Upon being revealed presenters will ask to be inserted within 
   * their parent presenters by firing a {@link SetContentEvent}
   * which will cause the parent to be revealed too.
   */
  void reveal();

  /**
   * Notify others that this presenter has been changed. This is especially
   * useful for stateful presenters that store parameters within the
   * history token. Calling this will make sure the history token is
   * updated with the right parameters.
   */
  void notifyChange();

  /**
   * <b>Important:</b> Make sure you call your parent class onReveal().
   * <p />
   * This method will be called whenever the presenter is revealed. Override
   * it to perform any action (such as refreshing content) that needs
   * to be done when the presenter is revealed.
   * <p />
   * This should never be called directly. Call {@link reveal()} instead.
   */
  void onReveal();
  
  /**
   * This method is called when a {@link Presenter} should prepare itself
   * based on a {@link PlaceRequest}. The presenter should extract
   * any parameters it needs from the request. A presenter should override
   * this method if it handles custom parameters, but it should call
   * the parent's <code>prepareFromRequest</code> method.
   *
   * @param request   The request.
   */
  public void prepareFromRequest( PlaceRequest request );

  /**
   * This method is called when creating a {@link PlaceRequest} for this
   * {@link Presenter}. The presenter should add all the required parameters to the 
   * request.
   * <p/>
   * <p/>
   * If nothing is to be done, simply return the <code>request</code>
   * unchanged. Otherwise, call {@link PlaceRequest#with(String, String)} to
   * add parameters. Eg:
   * <p/>
   * <pre>
   * return request.with( &quot;id&quot;, getId() );
   * </pre>
   * <p/>
   * A presenter should override this method if it handles custom parameters, but
   * it should call the parent's <code>prepareRequest</code> method.
   * 
   * @param request   The current request.
   * @return The prepared place request.
   */
  public PlaceRequest prepareRequest( PlaceRequest request );    


  /**
   * Makes it possible to access the {@link Widget} object associated with that presenter.
   * 
   * @return The Widget associated with that presenter.
   */
  public Widget getWidget();

}
