package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link EdgeMouseUpEvent} events.
 */
public interface EdgeMouseUpHandler extends EventHandler {

  /**
   * Called when EdgeMouseUpEvent is fired.
   * 
   * @param event the {@link EdgeMouseUpEvent} that was fired.
   */
  void onEdgeMouseUp(EdgeMouseUpEvent event);
}