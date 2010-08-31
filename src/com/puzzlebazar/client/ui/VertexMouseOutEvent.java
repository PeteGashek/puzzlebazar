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

import com.puzzlebazar.shared.util.Vec2i;

/**
 * Represents a mouse out event within a vertex of a square grid.
 * See {@link SquareGridManipulatorImpl} for details.
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
   * See {@link com.puzzlebazar.shared.util.Recti} for the difference between cell and vertex coordinates.
   * 
   * @param vertex The vertex coordinate.
   */
  public VertexMouseOutEvent(Vec2i vertex) {
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
