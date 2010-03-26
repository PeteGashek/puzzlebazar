package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link EdgeMouseDownEvent} events.
 */
public interface EdgeMouseDownHandler extends EventHandler {

  /**
   * Called when EdgeMouseDownEvent is fired.
   * 
   * @param event the {@link EdgeMouseDownEvent} that was fired.
   */
  void onEdgeMouseDown(EdgeMouseDownEvent event);
}