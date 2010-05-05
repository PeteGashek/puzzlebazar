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

package com.puzzlebazar.shared.puzzle.model;

import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.puzzlebazar.shared.model.HasId;

/**
 * The base interface of every different puzzle types.
 * 
 * @author Philippe Beaudoin
 */
public interface Puzzle<T extends Puzzle<?>> extends HasId<T>, Serializable {

  /**
   * Access the puzzle information contained in the attached 
   * {@link PuzzleDetails} structure.
   * 
   * @return The attached {@link PuzzleDetails}.
   */
  public PuzzleDetails<?> getPuzzleDetails();

  /**
   * Access the key to the puzzle information contained in the attached 
   * {@link PuzzleDetails} structure.
   * 
   * @return The key to the attached {@link PuzzleDetails}.
   */
  public Key<? extends PuzzleDetails<?>> getPuzzleDetailsKey();
}
