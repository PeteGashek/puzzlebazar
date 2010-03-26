package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link CellMouseOverEvent} events.
 */
public interface CellMouseOverHandler extends EventHandler {

  /**
   * Called when CellMouseOverEvent is fired.
   * 
   * @param event the {@link CellMouseOverEvent} that was fired.
   */
  void onCellMouseOver(CellMouseOverEvent event);
}