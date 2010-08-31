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

package com.puzzlebazar.shared.puzzle.heyawake.model;

/**
 * A class that indicates the removal of an existing {@link HeyawakeRoom} within
 * a {@link HeyawakePuzzle}.
 * 
 * @author Philippe Beaudoin
 */
public class DeleteHeyawakeRoomMove extends CreateHeyawakeRoomMove {

  /**
   * 
   */
  private static final long serialVersionUID = 2996570925165911406L;

  @SuppressWarnings("unused")
  private DeleteHeyawakeRoomMove() {
    // For serialization only
    super();
  }

  /**
   * Creates a move that indicates a new {@link HeyawakeRoom} should be
   * added to a {@link HeyawakePuzzle}.
   * 
   * @param room The {@link HeyawakeRoom} to delete. It must contain all the information
   *             of the room so that undo can work correctly, not just the room rectangle.
   */
  public DeleteHeyawakeRoomMove(HeyawakeRoom room) {
    super(room);
  }

  @Override
  public void doMove(HeyawakePuzzle puzzle) {
    undoMove(puzzle);
  }

  @Override
  public void undoMove(HeyawakePuzzle puzzle) {
    doMove(puzzle);
  }
  
}
