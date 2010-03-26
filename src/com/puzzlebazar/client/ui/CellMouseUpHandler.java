package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link CellMouseUpEvent} events.
 */
public interface CellMouseUpHandler extends EventHandler {

  /**
   * Called when CellMouseUpEvent is fired.
   * 
   * @param event the {@link CellMouseUpEvent} that was fired.
   */
  void onCellMouseUp(CellMouseUpEvent event);
}