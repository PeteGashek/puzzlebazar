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

import com.puzzlebazar.shared.model.ActionRightsInfo;
import com.puzzlebazar.shared.model.EditableObject;
import com.puzzlebazar.shared.model.HasKey;
import com.puzzlebazar.shared.model.User;
import com.puzzlebazar.shared.model.UserImpl;

/**
 * Basic information that every puzzle must provide.
 * This information is used when displaying the results of a search, 
 * so it will be accessed often and should remain as lightweight 
 * as possible. Any information that doesn't need to appear in
 * search results should be in the {@link PuzzleDetails} interface,
 * including a link to the puzzle itself. 
 * <p />
 * This interface doesn't allow manipulation of any information
 * related to the puzzle. To do this, see {@link PuzzleDetails}.
 * 
 * @author Philippe Beaudoin
 */
public interface PuzzleInfo extends Serializable, HasKey, EditableObject {

  /**
   * Access the title of the puzzle.
   * 
   * @return The title of the puzzle, or {@code null} if it is untitled.
   */
  public String getTitle();

  /**
   * Access the {@link PuzzleType} structure corresponding to the type
   * of the current puzzle.
   * 
   * @return The type of the puzzle, or {@code null} if the type is unknown.
   */
  public PuzzleType getPuzzleType();

  /**
   * Access a string that can give some information regarding the puzzle
   * size. For 2D puzzle this should be in the form "width x height".  
   * 
   * @return A string representing the size of the puzzle.
   */
  public String getSizeString();

  /**
   * Access the puzzle difficulty as a percentage.
   * 
   * @return A number between 0.0 and 1.0 indicating the difficulty. A value of 1.0 indicates a more difficult puzzle.
   */
  public double getDifficulty();

  /**
   * Access the puzzle quality as a percentage.
   * 
   * @return A number between 0.0 and 1.0 indicating the quality. A value of 1.0 indicates a puzzle of higher quality.
   */
  public double getQuality();

  /**
   * Verifies that the puzzle passes basic validity test. Typically, a valid puzzle is
   * one in which a valid solution was provided by the author.
   * <p />
   * {@code isValid()} must return {@code false} in all of the following situations:
   * <ul>
   * <li>{@link #isDeleted()} returns {@code true}</li>
   * </ul>
   * 
   * @return {@code true} if the puzzle was marked as complete, {@code false} otherwise.
   */
  public boolean isValid();
  
  /**
   * Verifies whether or not the puzzle was marked as completed by its author.
   * <p />
   * {@code isComplete()} must return {@code false} in all of the following situations:
   * <ul>
   * <li>{@link #isDeleted()} returns {@code true}</li>
   * <li>{@link #isValid()} returns {@code false}</li>
   * </ul>
   * 
   * @return {@code true} if the puzzle was marked as complete, {@code false} otherwise.
   */
  public boolean isComplete();

  
  /**
   * Verifies if this user has the required rights to call {@link #getPuzzleDetails}.
   * 
   * @param user The {@link UserImpl} who wants to perform the action.
   * @return The {@link ActionRightsInfo}. Call {@link ActionRightsInfo#canPerformAction()} 
   *         to verifies if the user has the required rights.
   */
  public ActionRightsInfo canUserViewPuzzleDetails( User user );

  /**
   * Access more information in the attached {@link PuzzleDetails} structure.
   * Gate keeper method is {@link #canUserViewPuzzleDetails}.
   * 
   * @return The attached {@link PuzzleDetails}, or {@code null} if it 
   *         has not been loaded.
   */
  public PuzzleDetails getPuzzleDetails();  
  
}
