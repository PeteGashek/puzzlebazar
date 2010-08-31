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
 * Represents a cell mouse over event within the cell of a square grid.
 * See {@link SquareGridManipulatorImpl} for details.
 */
public class CellMouseOverEvent extends CellEvent<CellMouseOverHandler> {

  /**
   * Event type for cell mouse over events. Represents the meta-data associated with
   * this event.
   */
  private static final Type<CellMouseOverHandler> TYPE = new Type<CellMouseOverHandler>();

  /**
   * Gets the event type associated with cell mouse over events.
   * 
   * @return the handler type
   */
  public static Type<CellMouseOverHandler> getType() {
    return TYPE;
  }
  
  /**
   * Creates a {@link CellMouseOverEvent} at the specified cell coordinate.
   * See {@link com.puzzlebazar.shared.util.Recti} for the difference between cell and vertex coordinates.
   * 
   * @param cell The cell coordinate.
   */
  public CellMouseOverEvent(Vec2i cell) {
    super(cell);
  }
  
  @Override
  public final Type<CellMouseOverHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(CellMouseOverHandler handler) {
    handler.onCellMouseOver(this);
  }

}
