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

package com.puzzlebazar.client.ui;

import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse move event within a vertex of a square grid.
 * See {@link SquareGridManipulatorImpl} for details.
 */
public class VertexMouseMoveEvent extends VertexEvent<VertexMouseMoveHandler> {

  /**
   * Event type for vertex mouse move events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<VertexMouseMoveHandler> TYPE = new Type<VertexMouseMoveHandler>();

  /**
   * Gets the event type associated with vertex mouse move events.
   * 
   * @return the handler type
   */
  public static Type<VertexMouseMoveHandler> getType() {
    return TYPE;
  }
  
  /**
   * Creates a {@link VertexMouseMoveEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param vertex The vertex coordinate.
   */
  public VertexMouseMoveEvent( Vec2i vertex ) {
    super(vertex);
  }

  @Override
  public final Type<VertexMouseMoveHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(VertexMouseMoveHandler handler) {
    handler.onVertexMouseMove(this);
  }


}
