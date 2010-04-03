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


import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse move event within an edge of a square grid.
 * See {@link SquareGridManipulatorImpl} for details.
 */
public class EdgeMouseMoveEvent extends EdgeEvent<EdgeMouseMoveHandler> {

  /**
   * Event type for edge mouse move events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<EdgeMouseMoveHandler> TYPE = new Type<EdgeMouseMoveHandler>();

  /**
   * Gets the event type associated with edge mouse move events.
   * 
   * @return the handler type
   */
  public static Type<EdgeMouseMoveHandler> getType() {
    return TYPE;
  }

  /**
   * Creates a {@link EdgeMouseMoveEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param vertical {@code true} if the edge is vertical, {@code false} if it is horizontal.
   * @param edge The edge coordinates. 
   *     For vertical edges, x must be a vertex coordinate and y must be a cell coordinate.
   *     For horizontal edges, x must be a cell coordinate and y must be a vertex coordinate.
   */
  public EdgeMouseMoveEvent( boolean vertical, Vec2i edge ) {
    super( vertical, edge );
  }

  @Override
  public final Type<EdgeMouseMoveHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EdgeMouseMoveHandler handler) {
    handler.onEdgeMouseMove(this);
  }


}
