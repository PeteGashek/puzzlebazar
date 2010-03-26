package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse down event within the cell of a square grid.
 * See {@link SquareGridManipulator} for details.
 */
public class CellMouseDownEvent extends CellEvent<CellMouseDownHandler> {

  /**
   * Event type for cell mouse down events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<CellMouseDownHandler> TYPE = new Type<CellMouseDownHandler>();

  /**
   * Gets the event type associated with cell mouse down events.
   * 
   * @return the handler type
   */
  public static Type<CellMouseDownHandler> getType() {
    return TYPE;
  }

  /**
   * Creates a {@link CellMouseDownEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param cell The cell coordinate.
   */
  public CellMouseDownEvent( Vec2i cell ) {
    super(cell);
  }

  @Override
  public final Type<CellMouseDownHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(CellMouseDownHandler handler) {
    handler.onCellMouseDown(this);
  }


}
