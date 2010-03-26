package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link EdgeMouseOutEvent} events.
 */
public interface EdgeMouseOutHandler extends EventHandler {

  /**
   * Called when EdgeMouseOutEvent is fired.
   * 
   * @param event the {@link EdgeMouseOutEvent} that was fired.
   */
  void onEdgeMouseOut(EdgeMouseOutEvent event);
}