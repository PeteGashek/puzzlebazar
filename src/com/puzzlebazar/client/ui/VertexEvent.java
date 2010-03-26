package com.puzzlebazar.client.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

public abstract class VertexEvent<H extends EventHandler> extends GwtEvent<H> {

  private final Vec2i vertex;
 
  /**
   * Creates a {@link VertexEvent} at the specified vertex coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param vertex The vertex coordinate.
   */
  public VertexEvent( Vec2i vertex ) {
    this.vertex = vertex;
  }
  
  /**
   * Retrieves the vertex coordinate associated with this event.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @return The vertex coordinate.
   */
  public Vec2i getVertex() {
    return vertex;
  }
}
