/**
 *
 */
package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * The interface for singleton view classes that handles all
 * the UI-related code for a {@link Presenter}.
 * 
 * @author Philippe Beaudoin
 */
public interface Display {

  /**
   * Retreives this display as a {@link Widget} so that it can be inserted within the DOM.
   * 
   * @return This display as a DOM object.
   */
  public Widget asWidget();

}