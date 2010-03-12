package com.philbeaudoin.platform.mvp.client;

import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyPlace;

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
  public abstract View getView();

  /**
   * <b>Important:</b> Make sure you call your superclass {@link #onReveal()}.
   * <p />
   * This method will be called whenever the presenter is revealed. Override
   * it to perform any action (such as refreshing content) that needs
   * to be done when the presenter is revealed.
   * <p />
   * This should never be called directly. Call 
   * {@link ProxyPlace#reveal()} instead.
   */
  public abstract void onReveal();

  /**
   * <b>Important:</b> Make sure you call your superclass {@link #onHide()}.
   * <p />
   * You should call this method on your child presenters:
   * <ul>
   * <li>Right before you remove it from the DOM; and</li>
   * <li>Whenever you receive a call to {@link #onHide()}.</li>
   * </ul>
   * Override this method to perform any clean-up operation. For example,
   * objects created directly or indirectly during the call to
   * {@link #onReveal()} should be disposed of in this methods.
   */
  public abstract void onHide();

  /**
   * Makes it possible to access the {@link Widget} object associated with that presenter.
   * 
   * @return The Widget associated with that presenter.
   */
  public abstract Widget getWidget();

}