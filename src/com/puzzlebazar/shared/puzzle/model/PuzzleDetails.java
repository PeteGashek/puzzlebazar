package com.puzzlebazar.shared.puzzle.model;

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

import java.util.List;

import com.puzzlebazar.shared.model.ActionRightsInfo;
import com.puzzlebazar.shared.model.Comment;
import com.puzzlebazar.shared.model.HasPrivacySettings;
import com.puzzlebazar.shared.model.Tag;
import com.puzzlebazar.shared.model.TagInstance;
import com.puzzlebazar.shared.model.User;

/**
 * Supplemental information regarding a puzzle but that does not need to
 * appear in search results. For information that needs to appear in search
 * results, see {@link PuzzleInfo}.
 * <p />
 * This interface offers all the same queries as {@link PuzzleInfo}. In 
 * addition, it offers all the methods required to manipulate puzzle 
 * information, whether this information is accessible via {@link PuzzleInfo} or
 * via {@link PuzzleDetails}.
 * 
 * @author Philippe Beaudoin
 */
public interface PuzzleDetails extends PuzzleInfo, HasPrivacySettings {

  /**
   * Access the comments attached to this puzzle. Only the comments that
   * can be legally viewed by this user should be returned.
   * 
   * @return The list of {@link Comment} attached to this puzzle.
   */
  public List<Comment> getComments();

  /**
   * Access the tags attached to this puzzle.
   * 
   * @return The list of {@link TagInstance} attached to this puzzle.
   */
  public List<TagInstance> getTagInstances();
 
  
  /**
   * Access the history of all the high-level actions performed on this puzzle.
   * 
   * @return The {@link PuzzleHistory}.
   */
  public PuzzleHistory getHistory();

  
  /**
   * Access all the {@link PuzzleSolve} available for this puzzle.
   * 
   * @return
   */
  List<PuzzleSolve> getSolves();
  

  /**
   * Verifies if this user has the required rights to call {@link #getPuzzle}.
   * 
   * @param user The {@link User} who wants to perform the action.
   * @return The {@link ActionRightsInfo}. Call {@link ActionRightsInfo#canPerformAction()} 
   *         to verifies if the user has the required rights.
   */
  public ActionRightsInfo canUserViewPuzzle( User user );

  /**
   * Access the attached {@link Puzzle}.
   * Gate keeper method is {@link #canUserViewPuzzle}.
   * 
   * @return The attached {@link Puzzle}, or {@code null} if it 
   *         has not been loaded.
   */
  public Puzzle getPuzzle();
  

  /**
   * Verifies if this user has the required rights to call {@link #setTitle}.
   * 
   * @param user The {@link User} who wants to perform the action.
   * @return The {@link ActionRightsInfo}. Call {@link ActionRightsInfo#canPerformAction()} 
   *         to verifies if the user has the required rights.
   */  
  public ActionRightsInfo canUserSetTitle( User user );

  /**
   * Sets a new title.
   * Gate keeper method is {@link #canUserSetTitle}.
   * 
   * @param title The new title for this puzzle.
   */
  public void setTitle( User user, String title );
  

  /**
   * Verifies if this user has the required rights to call {@link #tag} and {@link #untag}.
   * 
   * @param user The {@link User} who wants to perform the action.
   * @return The {@link ActionRightsInfo}. Call {@link ActionRightsInfo#canPerformAction()} 
   *         to verifies if the user has the required rights.
   */  
  public ActionRightsInfo canUserTag( User user, Tag tag );

  /**
   * Adds a tag to this puzzle, by a specific {@link User}.
   * Gate keeper method is {@link #canUserTag}.
   * 
   * @param user The {@link User} who wants to tag the puzzle.
   * @param tag The {@link Tag}.
   */
  public void tag( User user, Tag tag );

  /**
   * Removes a tag from this puzzle, by a specific {@link User}.
   * Gate keeper method is {@link #canUserTag}.
   * 
   * @param user The {@link User} who wants to untag the puzzle.
   * @param tag The {@link Tag}.
   */
  public void untag( User user, Tag tag );
  
  /**
   * Verifies if this user has the required rights to call {@link #addComment}.
   * 
   * @param user The {@link User} who wants to perform the action.
   * @return The {@link ActionRightsInfo}. Call {@link ActionRightsInfo#canPerformAction()} 
   *         to verifies if the user has the required rights.
   */  
  public ActionRightsInfo canUserComment( User user );
  
  /**
   * Adds a {@link Comment} to this puzzle.
   * Gate keeper method is {@link #canUserComment}.
   * 
   * @param comment The {@link Comment} to add.
   */
  public void addComment( Comment comment );
  
  
}
