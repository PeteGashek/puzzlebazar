package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse down event within a vertex of a square grid.
 * See {@link SquareGridManipulator} for details.
 */
public class VertexMouseDownEvent extends VertexEvent<VertexMouseDownHandler> {

  /**
   * Event type for vertex mouse down events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<VertexMouseDownHandler> TYPE = new Type<VertexMouseDownHandler>();

  /**
   * Gets the event type associated with vertex mouse down events.
   * 
   * @return the handler type
   */
  public static Type<VertexMouseDownHandler> getType() {
    return TYPE;
  }

  /**
   * Creates a {@link VertexMouseDownEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param vertex The vertex coordinate.
   */
  public VertexMouseDownEvent( Vec2i vertex ) {
    super(vertex);
  }

  @Override
  public final Type<VertexMouseDownHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(VertexMouseDownHandler handler) {
    handler.onVertexMouseDown(this);
  }


}
