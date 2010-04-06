package com.puzzlebazar.shared.puzzle.heyawake.model;

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
 * The interface of all moves that can be applied to an heyawake puzzle.
 * 
 * @author Philippe Beaudoin
 */
public interface HeyawakeMove {

  /**
   * Modifies the associated {@link HeyawakePuzzle} to apply the given move.
   * 
   * @param puzzle The {@link HeyawakePuzzle} on which to do the move.
   */
  public void doMove( HeyawakePuzzle puzzle );
  
  /**
   * Modifies the associated {@link HeyawakePuzzle} to unapply the given move.
   * 
   * @param puzzle The {@link HeyawakePuzzle} on which to do the move.
   */  
  public void undoMove( HeyawakePuzzle puzzle );
  
}
