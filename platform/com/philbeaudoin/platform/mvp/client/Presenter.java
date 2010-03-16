package com.philbeaudoin.platform.mvp.client;

import com.google.inject.Singleton;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceManager;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceRequest;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyBase;
import com.philbeaudoin.platform.mvp.client.proxy.SetContentEvent;

/**
 * A singleton presenter, one of the basic building block of
 * the <a href="http://code.google.com/intl/nl/events/io/2009/sessions/GoogleWebToolkitBestPractices.html">
 * model-view-presenter</a> architecture. Each page in your
 * application should correspond to a singleton {@link Presenter}
 * and it should have an accompanying singleton {@link View} and
 * {@link Proxy}.
 * 
 * @author Philippe Beaudoin
 */
@Singleton
public interface Presenter extends PresenterWidget {

  /**
   * <b>Important:</b> Do not call directly. Call {@link ProxyBase#reveal()}
   * instead. This way you can make sure you don't inadvertently reveal a 
   * non-leaf Presenter. Also, you will benefit from the change confirmation
   * mechanism. (See {@link PlaceManager#setOnLeaveConfirmation(String)}).
   * <p />
   * Requests the presenter to reveal itself on screen.
   * Upon being revealed presenters will ask to be inserted within 
   * their parent presenters by firing a {@link SetContentEvent}
   * which will cause the parent to be revealed too.
   */
  public void reveal();
  
  /**
   * This method is called when a {@link Presenter} should prepare itself
   * based on a {@link PlaceRequest}. The presenter should extract
   * any parameters it needs from the request. A presenter should override
   * this method if it handles custom parameters, but it should call
   * the parent's {@code prepareFromRequest} method.
   *
   * @param request   The request.
   */
  public void prepareFromRequest( PlaceRequest request );
  
  /**
   * Returns the {@link Proxy} for the current presenter.
   *
   * @return The proxy.
   */
  public Proxy<?> getProxy();

  /**
   * Notify others that this presenter has been changed. This is especially
   * useful for stateful presenters that store parameters within the
   * history token. Calling this will make sure the history token is
   * updated with the right parameters.
   */
  public void notifyChange();

  /**
   * This method is called when creating a {@link PlaceRequest} for this
   * {@link Presenter}. The presenter should add all the required parameters to the 
   * request.
   * <p/>
   * <p/>
   * If nothing is to be done, simply return the {@code request}
   * unchanged. Otherwise, call {@link PlaceRequest#with(String, String)} to
   * add parameters. Eg:
   * <p/>
   * <pre>
   * return request.with( &quot;id&quot;, getId() );
   * </pre>
   * <p/>
   * A presenter should override this method if it handles custom parameters, but
   * it should call the parent's {@code prepareRequest} method.
   * 
   * @param request   The current request.
   * @return The prepared place request.
   */
  public PlaceRequest prepareRequest( PlaceRequest request );

}
