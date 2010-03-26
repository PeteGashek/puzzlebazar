package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link EdgeMouseMoveEvent} events.
 */
public interface EdgeMouseMoveHandler extends EventHandler {

  /**
   * Called when EdgeMouseMoveEvent is fired.
   * 
   * @param event the {@link EdgeMouseMoveEvent} that was fired.
   */
  void onEdgeMouseMove(EdgeMouseMoveEvent event);
}