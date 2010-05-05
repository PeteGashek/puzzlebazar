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

import com.puzzlebazar.shared.util.Recti;

/**
 * Represents a room within a {@link HeyawakePuzzle}. 
 * 
 * @author Philippe Beaudoin
 */
public interface HeyawakeRoom extends Serializable {

  public static final int UNKNOWN_ROOM_VALUE = -1;
  
  /**
   * Access the value of the room, that is, how many filled cells it should contain.
   * Will return {@link #UNKNOWN_ROOM_VALUE} if the number of filled cells is unknown.
   * 
   * @return The value of the room, an integer, or {@link #UNKNOWN_ROOM_VALUE} if the value is unknown.
   */
  public int getRoomValue();
  
  /**
   * Returns the rectangle that represents the cells contained in the room. See
   * {@link Recti} for more information about cell and vertex coordinates.
   * 
   * @return The rectangle representing the room, a {@link Recti}.
   */
  public Recti getRoomRect();
  
}
