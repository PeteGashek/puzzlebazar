package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link VertexMouseDownEvent} events.
 */
public interface VertexMouseDownHandler extends EventHandler {

  /**
   * Called when VertexMouseDownEvent is fired.
   * 
   * @param event the {@link VertexMouseDownEvent} that was fired.
   */
  void onVertexMouseDown(VertexMouseDownEvent event);
}