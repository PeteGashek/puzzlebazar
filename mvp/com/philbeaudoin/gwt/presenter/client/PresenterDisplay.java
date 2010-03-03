/**
 *
 */
package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * The interface for singleton view classes that handles all
 * the UI-related code for a {@link Presenter}. 
 * <p />
 * This class is called PresenterDisplay instead of simply Display
 * because {@link Presenter} subclasses will usually define 
 * their own interface called Display and derived from this one. 
 * Naming this interface Display would therefore be impractical
 * for code-writing purposes.
 * 
 * @author Philippe Beaudoin
 */
public interface PresenterDisplay {

  /**
   * Retreives this display as a {@link Widget} so that it can be inserted within the DOM.
   * 
   * @return This display as a DOM object.
   */
  public Widget asWidget();

}