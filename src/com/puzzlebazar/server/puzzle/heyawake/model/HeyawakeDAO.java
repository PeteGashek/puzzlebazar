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

package com.puzzlebazar.server.puzzle.heyawake.model;

import com.google.inject.Inject;
import com.googlecode.objectify.ObjectifyFactory;
import com.gwtplatform.dispatch.shared.ActionException;
import com.puzzlebazar.server.model.DAOBase;
import com.puzzlebazar.server.model.UserDAO;
import com.puzzlebazar.server.puzzle.model.PuzzleDAO;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzle;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzleDetails;
import com.puzzlebazar.shared.puzzle.model.PuzzleInfoImpl;

/**
 * The class responsible of managing cache and datastore
 * storage of heyawake puzzle-related objects. This includes:
 * <ul>
 * <li> {@link HeyawakePuzzle}</li>
 * <li> {@link HeyawakePuzzleDetails}</li>
 * </ul>
 * 
 * @author Philippe Beaudoin
 */
public class HeyawakeDAO extends DAOBase {

  private static boolean objectsRegistered;

  private final UserDAO userDAO;
  private final PuzzleDAO puzzleDAO;
  
  @Override
  protected boolean areObjectsRegistered() {
    return objectsRegistered;
  }
  
  @Override
  protected void registerObjects(ObjectifyFactory ofyFactory) {
    objectsRegistered = true;
    ofyFactory.register(HeyawakePuzzle.class);
    ofyFactory.register(HeyawakePuzzleDetails.class);
  }
  
  @Inject
  public HeyawakeDAO(final ObjectifyFactory factory, final UserDAO userDAO, final PuzzleDAO puzzleDAO) {
    super(factory);
    this.userDAO = userDAO;
    this.puzzleDAO = puzzleDAO;
  }

  /**
   * Create a new heyawake puzzle in the datastore.
   *
   * @param width The desired width of the heyawake puzzle to create.
   * @param height The desired height of the heyawake puzzle to create.
   * @throws ActionException If the puzzle could not be created.
   */
  // TODO This is simplistic, we have to pass in more information and do a bit more here
  public HeyawakePuzzle createNewHeyawake(int width, int height) throws ActionException {    
    
    PuzzleInfoImpl puzzleInfo = new PuzzleInfoImpl(
        userDAO.getSessionUserKey(),
        "Untitled",
        Integer.toString(width) + " x " + height);        
    
    HeyawakePuzzleDetails puzzleDetails = new HeyawakePuzzleDetails(width, height);
    
    HeyawakePuzzle puzzle = new HeyawakePuzzle();
    
    puzzleDAO.createNewPuzzle(puzzleInfo, puzzleDetails, puzzle);
    
    return puzzle;
  } 
}
