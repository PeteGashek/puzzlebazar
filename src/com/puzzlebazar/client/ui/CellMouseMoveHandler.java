package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link CellMouseMoveEvent} events.
 */
public interface CellMouseMoveHandler extends EventHandler {

  /**
   * Called when CellMouseMoveEvent is fired.
   * 
   * @param event the {@link CellMouseMoveEvent} that was fired.
   */
  void onCellMouseMove(CellMouseMoveEvent event);
}