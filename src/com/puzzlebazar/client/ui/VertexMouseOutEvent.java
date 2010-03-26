package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse out event within a vertex of a square grid.
 * See {@link SquareGridManipulator} for details.
 */
public class VertexMouseOutEvent extends VertexEvent<VertexMouseOutHandler> {

  /**
   * Event type for vertex mouse out events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<VertexMouseOutHandler> TYPE = new Type<VertexMouseOutHandler>();

  /**
   * Gets the event type associated with vertex mouse out events.
   * 
   * @return the handler type
   */
  public static Type<VertexMouseOutHandler> getType() {
    return TYPE;
  }
  
  /**
   * Creates a {@link VertexMouseOutEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param vertex The vertex coordinate.
   */
  public VertexMouseOutEvent( Vec2i vertex ) {
    super(vertex);
  }

  @Override
  public final Type<VertexMouseOutHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(VertexMouseOutHandler handler) {
    handler.onVertexMouseOut(this);
  }


}
