package com.puzzlebazar.client.ui;

/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

public abstract class EdgeEvent<H extends EventHandler> extends GwtEvent<H> {

  public final boolean vertical;
  public final Vec2i edge;
  
  /**
   * Creates a {@link CellEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param vertical {@code true} if the edge is vertical, {@code false} if it is horizontal.
   * @param edge The edge coordinates. 
   *     For vertical edges, x must be a vertex coordinate and y must be a cell coordinate.
   *     For horizontal edges, x must be a cell coordinate and y must be a vertex coordinate.
   */
  public EdgeEvent( boolean vertical, Vec2i edge ) {
    this.vertical = vertical;
    this.edge = edge;
  }
  
  /**
   * Verifies if this is a vertical or a horizontal edge.
   * 
   * @return {@code true} if the edge is vertical, {@code false} if it is horizontal.
   */
  public boolean isVertical() {
    return vertical;
  }
  
  /**
   * Retrieves the edge coordinate associated with this event.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @return The edge coordinate.
   */
  public Vec2i getEdge() {
    return edge;
  }
}
