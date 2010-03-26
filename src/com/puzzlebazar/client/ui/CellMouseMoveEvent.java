package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse down event within the cell of a square grid.
 * See {@link SquareGridManipulator} for details.
 */
public class CellMouseMoveEvent extends CellEvent<CellMouseMoveHandler> {

  /**
   * Event type for cell mouse move events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<CellMouseMoveHandler> TYPE = new Type<CellMouseMoveHandler>();

  /**
   * Gets the event type associated with cell mouse move events.
   * 
   * @return the handler type
   */
  public static Type<CellMouseMoveHandler> getType() {
    return TYPE;
  }

  /**
   * Creates a {@link CellMouseMoveEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param cell The cell coordinate.
   */
  public CellMouseMoveEvent( Vec2i cell ) {
    super(cell);
  }

  @Override
  public final Type<CellMouseMoveHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(CellMouseMoveHandler handler) {
    handler.onCellMouseMove(this);
  }


}
