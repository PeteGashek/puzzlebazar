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

/**
 * Objects that implement that interface are able to verify whether
 * or not a {@link CellState} verifies a desired consition.
 * 
 * @param <S> The type of {@link CellState} for this condition.
 * 
 * @author Philippe Beaudoin
 */
public interface CellStateCondition<S extends CellState> {

  /**
   * Checks if the state verifies the condition.
   * 
   * @param state The {@link CellState} to check.
   * @return {@code true} if the state verifies the condition, {@code false} otherwise.
   */
  boolean doesCellVerifyCondition( S state );
  
}
