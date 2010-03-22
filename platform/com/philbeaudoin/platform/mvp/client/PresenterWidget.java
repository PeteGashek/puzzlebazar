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
   * TODO this is no longer the right way to reveal a presenter. Update the
   * documentation. This should probably be called notifyReveal().
   * <p />
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
   * This method sets some content in a specific slot of the {@link Presenter}.
   * 
   * @param slot An opaque object identifying
   *             which slot this content is being set into. The attached view should know
   *             what to do with this slot.
   * @param content The content, a {@link PresenterWidget}. Passing {@code null} will clear the slot.
   */
  void setContent(Object slot, PresenterWidget content);

  /**
   * This method adds some content in a specific slot of the {@link Presenter}.
   * 
   * @param slot An opaque object identifying
   *             which slot this content is being added into. The attached view should know
   *             what to do with this slot.
   * @param content The content, a {@link PresenterWidget}. Passing {@code null} will not do anything.
   */
  void addContent(Object slot, PresenterWidget content);

  /**
   * This method clears the content in a specific slot.
   * 
   * @param slot An opaque object of type identifying
   *             which slot to clear. The attached view should know
   *             what to do with this slot.
   */
  void clearContent(Object slot);

  /**
   * Makes it possible to access the {@link Widget} object associated with that presenter.
   * 
   * @return The Widget associated with that presenter.
   */
  public Widget getWidget();
}