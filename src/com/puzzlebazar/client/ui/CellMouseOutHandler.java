package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link CellMouseOutEvent} events.
 */
public interface CellMouseOutHandler extends EventHandler {

  /**
   * Called when CellMouseOutEvent is fired.
   * 
   * @param event the {@link CellMouseOutEvent} that was fired.
   */
  void onCellMouseOut(CellMouseOutEvent event);
}