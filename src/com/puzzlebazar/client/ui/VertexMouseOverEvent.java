package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse over event within a vertex of a square grid.
 * See {@link SquareGridManipulator} for details.
 */
public class VertexMouseOverEvent extends VertexEvent<VertexMouseOverHandler> {

  /**
   * Event type for vertex mouse over events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<VertexMouseOverHandler> TYPE = new Type<VertexMouseOverHandler>();

  /**
   * Gets the event type associated with vertex mouse over events.
   * 
   * @return the handler type
   */
  public static Type<VertexMouseOverHandler> getType() {
    return TYPE;
  }  

  /**
   * Creates a {@link VertexMouseOverEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param vertex The vertex coordinate.
   */
  public VertexMouseOverEvent( Vec2i vertex ) {
    super(vertex);
  }

  @Override
  public final Type<VertexMouseOverHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(VertexMouseOverHandler handler) {
    handler.onVertexMouseOver(this);
  }


}
