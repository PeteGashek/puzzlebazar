package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a cell mouse up event within the cell of a square grid.
 * See {@link SquareGridManipulatorImpl} for details.
 */
public class CellMouseUpEvent extends CellEvent<CellMouseUpHandler> {

  /**
   * Event type for cell mouse up events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<CellMouseUpHandler> TYPE = new Type<CellMouseUpHandler>();

  /**
   * Gets the event type associated with mouse down events.
   * 
   * @return the handler type
   */
  public static Type<CellMouseUpHandler> getType() {
    return TYPE;
  }
  
  /**
   * Creates a {@link CellMouseUpEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param cell The cell coordinate.
   */
  public CellMouseUpEvent( Vec2i cell ) {
    super(cell);
  }

  @Override
  public final Type<CellMouseUpHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(CellMouseUpHandler handler) {
    handler.onCellMouseUp(this);
  }


}
