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

/**
 * Objects that implement that interface are able to verify that two cell
 * states match according to a desired condition.
 *
 * @param <S> The type of {@link CellState} for this matcher.
 *
 * @author Philippe Beaudoin
 */
public interface CellStateMatcher<S extends CellState> {

  /**
   * Verifies if two different states matches.
   *
   * @param stateA The first {@link CellState}.
   * @param stateB The second {@link CellState}.
   * @return {@code true} if the states match, {@code false} otherwise.
   */
  boolean doCellMatch(S stateA, S stateB);

}
