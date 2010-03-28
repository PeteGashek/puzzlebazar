package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse over event within an edge of a square grid.
 * See {@link SquareGridManipulatorImpl} for details.
 */
public class EdgeMouseOverEvent extends EdgeEvent<EdgeMouseOverHandler> {

  /**
   * Event type for edge mouse over events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<EdgeMouseOverHandler> TYPE = new Type<EdgeMouseOverHandler>();

  /**
   * Gets the event type associated with edge mouse over events.
   * 
   * @return the handler type
   */
  public static Type<EdgeMouseOverHandler> getType() {
    return TYPE;
  }
  
  /**
   * Creates a {@link EdgeMouseOverEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param vertical {@code true} if the edge is vertical, {@code false} if it is horizontal.
   * @param edge The edge coordinates. 
   *     For vertical edges, x must be a vertex coordinate and y must be a cell coordinate.
   *     For horizontal edges, x must be a cell coordinate and y must be a vertex coordinate.
   */
  public EdgeMouseOverEvent( boolean vertical, Vec2i edge ) {
    super( vertical, edge );
  }

  @Override
  public final Type<EdgeMouseOverHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EdgeMouseOverHandler handler) {
    handler.onEdgeMouseOver(this);
  }


}
