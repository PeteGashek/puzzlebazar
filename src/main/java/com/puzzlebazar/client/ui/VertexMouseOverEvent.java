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
 * Represents a mouse over event within a vertex of a square grid.
 * See {@link SquareGridManipulatorImpl} for details.
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
   * See {@link com.puzzlebazar.shared.util.Recti} for the difference between cell and vertex coordinates.
   *
   * @param vertex The vertex coordinate.
   */
  public VertexMouseOverEvent(Vec2i vertex) {
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
