package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a cell mouse over event within the cell of a square grid.
 * See {@link SquareGridManipulator} for details.
 */
public class CellMouseOverEvent extends CellEvent<CellMouseOverHandler> {

  /**
   * Event type for cell mouse over events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<CellMouseOverHandler> TYPE = new Type<CellMouseOverHandler>();

  /**
   * Gets the event type associated with cell mouse over events.
   * 
   * @return the handler type
   */
  public static Type<CellMouseOverHandler> getType() {
    return TYPE;
  }
  
  /**
   * Creates a {@link CellMouseOverEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param cell The cell coordinate.
   */
  public CellMouseOverEvent( Vec2i cell ) {
    super(cell);
  }
  
  @Override
  public final Type<CellMouseOverHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(CellMouseOverHandler handler) {
    handler.onCellMouseOver(this);
  }


}
