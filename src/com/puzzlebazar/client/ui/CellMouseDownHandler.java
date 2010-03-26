package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link CellMouseDownEvent} events.
 */
public interface CellMouseDownHandler extends EventHandler {

  /**
   * Called when CellMouseDownEvent is fired.
   * 
   * @param event the {@link CellMouseDownEvent} that was fired.
   */
  void onCellMouseDown(CellMouseDownEvent event);
}