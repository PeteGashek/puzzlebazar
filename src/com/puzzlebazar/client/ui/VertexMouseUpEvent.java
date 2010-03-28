package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse up event within a vertex of a square grid.
 * See {@link SquareGridManipulatorImpl} for details.
 */
public class VertexMouseUpEvent extends VertexEvent<VertexMouseUpHandler> {

  /**
   * Event type for vertex mouse up events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<VertexMouseUpHandler> TYPE = new Type<VertexMouseUpHandler>();

  /**
   * Gets the event type associated with vertex mouse up events.
   * 
   * @return the handler type
   */
  public static Type<VertexMouseUpHandler> getType() {
    return TYPE;
  }
  /**
   * Creates a {@link VertexMouseUpEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param vertex The vertex coordinate.
   */
  public VertexMouseUpEvent( Vec2i vertex ) {
    super(vertex);
  }

  @Override
  public final Type<VertexMouseUpHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(VertexMouseUpHandler handler) {
    handler.onVertexMouseUp(this);
  }


}
