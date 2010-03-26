package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link EdgeMouseOverEvent} events.
 */
public interface EdgeMouseOverHandler extends EventHandler {

  /**
   * Called when EdgeMouseOverEvent is fired.
   * 
   * @param event the {@link EdgeMouseOverEvent} that was fired.
   */
  void onEdgeMouseOver(EdgeMouseOverEvent event);
}