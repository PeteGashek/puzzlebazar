package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link VertexMouseMoveEvent} events.
 */
public interface VertexMouseMoveHandler extends EventHandler {

  /**
   * Called when VertexMouseMoveEvent is fired.
   * 
   * @param event the {@link VertexMouseMoveEvent} that was fired.
   */
  void onVertexMouseMove(VertexMouseMoveEvent event);
}