package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse out event within the cell of a square grid.
 * See {@link SquareGridManipulatorImpl} for details.
 */
public class CellMouseOutEvent extends CellEvent<CellMouseOutHandler> {

  /**
   * Event type for cell mouse out events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<CellMouseOutHandler> TYPE = new Type<CellMouseOutHandler>();

  /**
   * Gets the event type associated with cell mouse out events.
   * 
   * @return the handler type
   */
  public static Type<CellMouseOutHandler> getType() {
    return TYPE;
  }

  /**
   * Creates a {@link CellMouseOutEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param cell The cell coordinate.
   */
  public CellMouseOutEvent( Vec2i cell ) {
    super(cell);
  }


  @Override
  public final Type<CellMouseOutHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(CellMouseOutHandler handler) {
    handler.onCellMouseOut(this);
  }


}
