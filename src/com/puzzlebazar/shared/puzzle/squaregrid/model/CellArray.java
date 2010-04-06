package com.puzzlebazar.shared.puzzle.squaregrid.model;

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

import com.puzzlebazar.shared.util.Has2DSize;
import com.puzzlebazar.shared.util.Vec2i;


/**
 * An array of states attached to cells. A state is simply an integer value.
 *
 * @param <S> The {@link CellState} of the states contained in the cell array.
 * 
 * @author Philippe Beaudoin
 */
public interface CellArray<S extends CellState> extends Has2DSize {
  
  /**
   * Changes the state at the provided cell location.
   * Will fire a {@link SetCellStateEvent}.
   * 
   * @param cell Cell coordinate at which to set the state, a {@link Vec2i}.
   * @param state The desired {@link CellState}.
   */
  public void setCellState(Vec2i loc, S state);
  
  /**
   * Access the state at the provided cell location.
   * 
   * @param loc Cell coordinate at which to access the state, a {@link Vec2i}.
   * @return The current {@link CellState} at that coordinate.
   */
  public S getCellState(Vec2i loc);

  /**
   * Resets all the states in the grid to their default value.
   * Will fire a {@link ClearCellsStateEvent}.
   */
  public void clearCellStates();



}

