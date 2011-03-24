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

package com.puzzlebazar.server.puzzle.model;

import com.google.inject.Inject;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.gwtplatform.dispatch.shared.ActionException;
import com.puzzlebazar.server.model.DAOBase;
import com.puzzlebazar.shared.ObjectAlreadyCreatedException;
import com.puzzlebazar.shared.TransactionFailedException;
import com.puzzlebazar.shared.puzzle.model.PuzzleDetailsImpl;
import com.puzzlebazar.shared.puzzle.model.PuzzleImpl;
import com.puzzlebazar.shared.puzzle.model.PuzzleInfoImpl;

/**
 * The class responsible of managing cache and datastore
 * storage of puzzle-related objects. This includes:
 * <ul>
 * <li>{@link PuzzleInfoImpl}</li>
 * </ul>
 *
 * @author Philippe Beaudoin
 */
public class PuzzleDAO extends DAOBase {

  private static boolean objectsRegistered;

  @Override
  protected boolean areObjectsRegistered() {
    return objectsRegistered;
  }

  @Override
  protected void registerObjects(ObjectifyFactory ofyFactory) {
    objectsRegistered = true;
    ofyFactory.register(PuzzleInfoImpl.class);
  }

  @Inject
  public PuzzleDAO(final ObjectifyFactory objectifyFactory) {
    super(objectifyFactory);
  }

  /**
   * Create a new puzzle in the datastore given its information.
   * This should be called only by specific puzzle DAOs such as {@code HeyawakeDAO}.
   *
   * @param puzzleInfo Information on the to save in the datastore.
   * @throws ActionException If the puzzle could not be created.
   */
  public <D extends PuzzleDetailsImpl<?>> void createNewPuzzle(
      PuzzleInfoImpl puzzleInfo,
      D puzzleDetails,
      PuzzleImpl<?, D> puzzle) throws ActionException {

    // TODO ensure rights?

    if (puzzleInfo.getId() != null) {
      throw new ObjectAlreadyCreatedException("Puzzle info already has an id: " + puzzleInfo.getId());
    }

    Objectify ofyTxn = newOfyTransaction();

    try {
      ofyTxn.put(puzzleInfo);
      puzzleDetails.attachToPuzzleInfo(puzzleInfo);
      ofyTxn.put(puzzleDetails);
      puzzleInfo.setPuzzleDetailsId(puzzleDetails.getId());
      ofyTxn.put(puzzleInfo);
      puzzle.attachToPuzzleDetails(puzzleDetails);
      ofyTxn.put(puzzle);
      puzzleDetails.setPuzzleId(puzzle.getId());
      ofyTxn.put(puzzleDetails);
      ofyTxn.getTxn().commit();
    } finally {
      if (ofyTxn.getTxn().isActive()) {
        ofyTxn.getTxn().rollback();
        throw new TransactionFailedException("Cannot create puzzle, title = \"" + puzzleInfo.getTitle() + "\"");
      }
    }
  }

}
