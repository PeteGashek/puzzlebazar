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

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

public abstract class CellEvent<H extends EventHandler> extends GwtEvent<H> {

  private final Vec2i cell;
 
  /**
   * Creates a {@link CellEvent} at the specified cell coordinate.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @param cell The cell coordinate.
   */
  public CellEvent( Vec2i cell ) {
    this.cell = cell;
  }
  
  /**
   * Retrieves the cell coordinate associated with this event.
   * See {@link Recti} for the difference between cell and vertex coordinates.
   * 
   * @return The cell coordinate.
   */
  public Vec2i getCell() {
    return cell;
  }
}
