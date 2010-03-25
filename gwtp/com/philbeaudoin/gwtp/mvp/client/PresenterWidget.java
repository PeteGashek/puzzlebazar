package com.philbeaudoin.gwtp.mvp.client;

import com.google.gwt.user.client.ui.Widget;

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
public interface PresenterWidget extends HandlerContainer {

  /**
   * Returns the {@link View} for the current presenter.
   *
   * @return The view.
   */
  public View getView();

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

  /**
   * <b>Important:</b> Make sure you call your superclass {@link #onReveal()}.
   * <p />
   * This method will be called whenever the presenter is revealed. Override
   * it to perform any action (such as refreshing content) that needs
   * to be done when the presenter is revealed.
   */
  void onReveal();

  /**
   * <b>Important:</b> Make sure you call your superclass {@link #onHide()}.
   * <p />
   * Override this method to perform any clean-up operations. For example,
   * objects created directly or indirectly during the call to
   * {@link #onReveal()} should be disposed of in this methods.
   */
  void onHide();
}