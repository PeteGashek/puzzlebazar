package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

public abstract class CellEvent<H extends EventHandler> extends GwtEvent<H> {

  private final Vec2i cell;
 
  /**
   * Creates a {@link CellEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param cell The cell coordinate.
   */
  public CellEvent( Vec2i cell ) {
    this.cell = cell;
  }
  
  /**
   * Retrieves the cell coordinate associated with this event.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @return The cell coordinate.
   */
  public Vec2i getCell() {
    return cell;
  }
}
