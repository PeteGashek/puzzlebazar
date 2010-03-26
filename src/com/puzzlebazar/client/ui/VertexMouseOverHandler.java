package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link VertexMouseOverEvent} events.
 */
public interface VertexMouseOverHandler extends EventHandler {

  /**
   * Called when VertexMouseOverEvent is fired.
   * 
   * @param event the {@link VertexMouseOverEvent} that was fired.
   */
  void onVertexMouseOver(VertexMouseOverEvent event);
}