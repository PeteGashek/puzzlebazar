package com.philbeaudoin.platform.mvp.client;

import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceManager;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyRaw;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentEvent;

/**
 * A presenter that does not have to be a singleton. Single page
 * from your application will usually implement the singleton
 * {@link Presenter} interface. Use the {@link PresenterWidget}
 * interface for complex widget that need to interact with 
 * model objects, but that can be instantiated in many different
 * places in your application.
 * 
 * @author Philippe Beaudoin
 */
public interface PresenterWidget {

  /**
   * Returns the {@link View} for the current presenter.
   *
   * @return The view.
   */
  public View getView();

  /**
   * <b>Important:</b> If you want to reveal a presenter, from within
   * your application, you should call {@link ProxyRaw#reveal()}
   * instead. This way you can make sure you don't inadvertently reveal a 
   * non-leaf Presenter. Also, you will benefit from the change confirmation
   * mechanism. (See {@link PlaceManager#setOnLeaveConfirmation(String)}).
   * <p />
   * Requests the presenter to reveal itself on screen. Nothing
   * happens if the presenter is currently visible (see {@link #isVisible()}).
   * If this class inherits from {@link PresenterImpl},
   * upon being revealed it will ask to be inserted within 
   * its parent presenter by firing a {@link RevealContentEvent}.
   * This will cause the parent to be revealed too.
   */
  public void reveal();

  /**
   * <b>Important:</b> You should call this method on a child presenter
   * whenever it is being removed from the DOM. This typically happens
   * when its being replaced with a different presenter. 
   * <p />
   * Notifies the presenter that its being hidden itself. Nothing
   * happens if the presenter is currently not visible (see {@link #isVisible()}).
   * If this class inherits from {@link PresenterImpl},
   * upon being revealed it will ask to be inserted within 
   * its parent presenter by firing a {@link RevealContentEvent}.
   * This will cause the parent to be revealed too.
   */
  public void notifyHide();

  /**
   * Verifies if the presenter is currently visible on the screen. A
   * presenter should be visible if it successfully revealed itself
   * and was not hidden later.
   * 
   * @return {@code true} if the presenter is visible, {@code false} otherwise.
   */
  public boolean isVisible();

  /**
   * Makes it possible to access the {@link Widget} object associated with that presenter.
   * 
   * @return The Widget associated with that presenter.
   */
  public Widget getWidget();

}