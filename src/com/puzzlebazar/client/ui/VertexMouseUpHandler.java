package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link VertexMouseUpEvent} events.
 */
public interface VertexMouseUpHandler extends EventHandler {

  /**
   * Called when VertexMouseUpEvent is fired.
   * 
   * @param event the {@link VertexMouseUpEvent} that was fired.
   */
  void onVertexMouseUp(VertexMouseUpEvent event);
}