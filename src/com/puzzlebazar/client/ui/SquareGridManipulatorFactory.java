package com.puzzlebazar.client.ui;

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.user.client.ui.Widget;

/**
 * The factory class to produce {@link SquareGridManipulatorImpl} objects.
 * 
 * @author Philippe Beaudoin
 */
public interface SquareGridManipulatorFactory {

  /**
   * Creates a {@link SquareGridManipulator} attached to a panel 
   * and a UI widget.
   * 
   * @param gridPanel The {@link SquareGridLayoutPanel} on which to add a manipulator.
   * @param uiWidget The {@link Widget} receiving the ui input. Must implement
   *   the following interfaces: {@link HasMouseDownHandlers},
   *   {@link HasMouseUpHandlers}, {@link HasMouseMouveHandlers},
   *   {@link HasMouseOutHandlers}.
   */  
  public SquareGridManipulator create(
      SquareGridLayoutPanel gridPanel,
      Widget uiWidget );
}
