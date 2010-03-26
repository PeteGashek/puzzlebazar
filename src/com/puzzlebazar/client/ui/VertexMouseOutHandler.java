package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link VertexMouseOutEvent} events.
 */
public interface VertexMouseOutHandler extends EventHandler {

  /**
   * Called when VertexMouseOutEvent is fired.
   * 
   * @param event the {@link VertexMouseOutEvent} that was fired.
   */
  void onVertexMouseOut(VertexMouseOutEvent event);
}