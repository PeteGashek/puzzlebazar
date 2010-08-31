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

import java.io.Serializable;

import com.puzzlebazar.shared.util.KeyNotFoundException;

/**
 * A class that indicates the addition of a new {@link HeyawakeRoom} within
 * a {@link HeyawakePuzzle}.
 * 
 * @author Philippe Beaudoin
 */
public class CreateHeyawakeRoomMove implements Serializable, HeyawakeMove {

  private static final long serialVersionUID = 4443972871090200529L;
  
  private HeyawakeRoom room;

  protected CreateHeyawakeRoomMove() {
    // For serialization only
    room = null;
  }

  /**
   * Creates a move that indicates a new {@link HeyawakeRoom} should be
   * added to a {@link HeyawakePuzzle}.
   * 
   * @param room The {@link HeyawakeRoom} to create.
   */
  public CreateHeyawakeRoomMove(HeyawakeRoom room) {
    this.room = room; 
  }
  
  /**
   * Access the {@link HeyawakeRoom} attached to this move.
   * 
   * @return The {@link HeyawakeRoom}.
   */
  public HeyawakeRoom getRoom() {
    return room;
  }

  @Override
  public void doMove(HeyawakePuzzle puzzle) {
    puzzle.addRoom(room);
  }

  @Override
  public void undoMove(HeyawakePuzzle puzzle) {
    try {
      puzzle.deleteRoom(room.getRoomRect().getCell00());
    } catch (KeyNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  
}
