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

package com.puzzlebazar.shared.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that can be used to give more information on an error
 * that occured within a square puzzle grid.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzleMessage {

  private final boolean error;
  
  private String message;
  
  private final List<Vec2i> errorLocations;
  
  /**
   * Creates a {@link PuzzleMessage} without a message. This {@link PuzzleMessage} can represent
   * an error message or not, depending on the value of the {@code error} parameter.
   * 
   * @param error Pass {@code true} if this represents an erroneous message, {@code false} otherwise.
   */
  public PuzzleMessage( boolean error ) {
    this.error = error;
    message = null;
    if( error )
      errorLocations = new ArrayList<Vec2i>();
    else
      errorLocations = null;
  }
  
  /**
   * Creates a {@link PuzzleMessage} with a message. This {@link PuzzleMessage} can represent
   * an error message or not, depending on the value of the {@code error} parameter.
   * 
   * @param error Pass {@code true} if this represents an erroneous message, {@code false} otherwise.
   * @param message The message to attach to this {@link PuzzleMessage}.
   */
  public PuzzleMessage(  boolean error, String message ) {
    this.error = error;
    this.message = message;
    if( error )
      errorLocations = new ArrayList<Vec2i>();
    else
      errorLocations = null;
  }
  
  /**
   * Adds an error location to this {@link PuzzleMessage}. Works only if the {@link PuzzleMessage} represents
   * an error message.
   * 
   * @param location The location to add, a {@link Vec2i}.
   */
  public void addErrorLocation( Vec2i location ) {
    assert errorLocations != null : "Can only add locations to a PuzzleMessage representing an erroneous message.";
    errorLocations.add(location);
  }

  /**
   * Access all the locations marked within this {@link PuzzleMessage}. If this is not an error message,
   * then returns {@code null}.
   * 
   * @return A {@link List} of locations, which are {@link Vec2i}. Returns {@code null} if this is not an error message.
   */
  public List<Vec2i> getErrorLocations() {
    return errorLocations;
  }
  
  /**
   * Retrieves the message associated with this {@link PuzzleMessage}.
   * 
   * @return The message, a {@link String}.
   */
  public String getMessage() {
    return message;
  }
  
  /**
   * Sets the message associated with this {@link PuzzleMessage}. The previous message
   * will be lost.
   * 
   * @param message The message, a {@link String}
   */
  public void setMessage( String message ) {
    this.message = message;
  }
  
  /**
   * Checks if this {@link PuzzleMessage} represents an error message or not.
   * 
   * @return {@code true} if this is an an error message, {@code false} otherwise.
   */
  public boolean isError() {
    return error;
  }
  
}
