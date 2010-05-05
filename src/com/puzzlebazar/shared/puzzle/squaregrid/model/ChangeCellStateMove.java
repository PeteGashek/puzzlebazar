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

package com.puzzlebazar.shared.puzzle.squaregrid.model;

import java.io.Serializable;

import com.puzzlebazar.shared.util.Vec2i;

/**
 * A class that indicates a {@link CellState} change within a {@link CellArray}.
 *
 * @param <S> The type of {@link CellState} for this move.
 * 
 * @author Philippe Beaudoin
 */
public class ChangeCellStateMove<S extends CellState> implements Serializable {

  private static final long serialVersionUID = 9128866174149471307L;
  
  private Vec2i loc;
  
  private S stateBefore;
  private S stateAfter;

  protected ChangeCellStateMove() {
    // For serialization only
    stateBefore = stateAfter = null;
  }

  /**
   * Creates a move to indicate a change of state inside a {@link CellArray}.
   * Pass both the state before the change and the state after the change,
   * making it possible to undo this event.
   * 
   * @param loc The location of the cell on which to change state. A {@link Vec2i}.
   * @param stateBefore The {@link CellState} before the change.
   * @param stateAfter  The {@link CellState} after the change.
   */
  public ChangeCellStateMove( Vec2i loc, S stateBefore, S stateAfter ) {
    this.loc = loc;
    this.stateBefore = stateBefore; 
    this.stateAfter = stateAfter;
  }
  
  /**
   * @return The locatopm pf tje cell to change. A {@link Vec2i}.
   */
  public Vec2i getLoc() {
    return loc;
  }
  
  /**
   * @return The {@link CellState} that was in the cell before the change.
   */
  public S getStateBefore() {
    return stateBefore;
  }

  /**
   * @return The {@link CellState} that should be in the cell after the change.
   */
  public S getStateAfter() {
    return stateAfter;
  }

}
