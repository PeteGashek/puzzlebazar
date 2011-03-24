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
 * Represents a mouse down event within the cell of a square grid.
 * See {@link SquareGridManipulatorImpl} for details.
 */
public class CellMouseMoveEvent extends CellEvent<CellMouseMoveHandler> {

  /**
   * Event type for cell mouse move events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<CellMouseMoveHandler> TYPE = new Type<CellMouseMoveHandler>();

  /**
   * Gets the event type associated with cell mouse move events.
   *
   * @return the handler type
   */
  public static Type<CellMouseMoveHandler> getType() {
    return TYPE;
  }

  /**
   * Creates a {@link CellMouseMoveEvent} at the specified cell coordinate.
   * See {@link com.puzzlebazar.shared.util.Recti} for the difference between cell and vertex coordinates.
   *
   * @param cell The cell coordinate.
   */
  public CellMouseMoveEvent(Vec2i cell) {
    super(cell);
  }

  @Override
  public final Type<CellMouseMoveHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(CellMouseMoveHandler handler) {
    handler.onCellMouseMove(this);
  }

}
