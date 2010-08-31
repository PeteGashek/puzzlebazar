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

import javax.persistence.Id;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.gwtplatform.mvp.client.HandlerContainerImpl;
import com.puzzlebazar.shared.InvalidObjectException;
import com.puzzlebazar.shared.ObjectAlreadyInitializedException;

/**
 * @param <T> The {@link Puzzle} type.
 * @param <D> The {@link PuzzleDetails} type.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzleImpl<T extends Puzzle<?>, D extends PuzzleDetails<?>> 
    extends HandlerContainerImpl implements Puzzle<T> {

  private static final long serialVersionUID = 5993754990665635539L;

  @Id Long id;

  @Parent private Key<D> puzzleDetailsKey;
  // TODO This need to be transient, we don't want to send it over the wire 
  @Transient protected D puzzleDetails;

  public PuzzleImpl() {
    super(false);
  }
  
  /**
   * Attaches this puzzle to its {@link PuzzleDetails}. This is only
   * meant to be used within {@link PuzzleDAO}.
   * 
   * @param puzzleDetails The {@link PuzzleDetails} to attach to this puzzle.
   * @throws ObjectAlreadyInitializedException If the puzzle is already attached to puzzle details.
   * @throws InvalidObjectException If the attached puzzle details are invalid for this puzzle.
   */
  @SuppressWarnings("unchecked")
  public void attachToPuzzleDetails(D puzzleDetails) 
      throws ObjectAlreadyInitializedException, InvalidObjectException {
    if (this.puzzleDetails != null) {
      throw new ObjectAlreadyInitializedException("PuzzleDetails already set in PuzzleImpl.");
    }
    if (puzzleDetailsKey == null) {
      puzzleDetailsKey = (Key<D>) puzzleDetails.createKey();
    } else if (puzzleDetails.getId() != puzzleDetailsKey.getId()) {
      throw new InvalidObjectException("PuzzleDetails id doesn't match the one in PuzzleImpl.");
    }
    this.puzzleDetails = puzzleDetails;
  }

  @Override
  public PuzzleDetails<?> getPuzzleDetails() {
    return puzzleDetails;
  }

  @Override
  public Key<? extends PuzzleDetails<?>> getPuzzleDetailsKey() {
    return puzzleDetailsKey;
  }

  @Override
  public Key<T> createKey() {
    return null;
  }

  @Override
  public Long getId() {
    return id;
  }
  
}
