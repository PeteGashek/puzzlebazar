package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.gwt.event.shared.EventHandler;

public interface SetContentHandler extends EventHandler {

  /**
   * Called whenever a presenter wants to sets itself as content on some
   * parent presenter's slot.
   * 
   * @param setContentEvent The event containing the presenter that wants to bet set as content.
   */
  void onSetContent(SetContentEvent setContentEvent);

}
