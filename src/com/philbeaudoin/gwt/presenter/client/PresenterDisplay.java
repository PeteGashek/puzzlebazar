/**
 *
 */
package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.user.client.ui.Widget;

public interface PresenterDisplay {
  
  /**
   * Retreives this display as a {@link Widget} so that it can be inserted within the DOM.
   * 
   * @return This display as a DOM object.
   */
  public Widget asWidget();

}