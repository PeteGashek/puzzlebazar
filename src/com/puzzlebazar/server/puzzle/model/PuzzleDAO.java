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
import com.googlecode.objectify.ObjectifyFactory;
import com.puzzlebazar.server.model.DAOBase;
import com.puzzlebazar.shared.ObjectAlreadyCreatedException;
import com.puzzlebazar.shared.puzzle.model.PuzzleInfoImpl;


/**
 * The class responsible of managing cache and datastore
 * storage of puzzle-related objects, including:
 * <ul>
 * <li> {@link PuzzleInfoImpl}
 * </ul>
 * 
 * @author Philippe Beaudoin
 */
public class PuzzleDAO extends DAOBase {

  @Override
  protected void registerObjects(ObjectifyFactory ofyFactory) {
    ofyFactory.register(PuzzleInfoImpl.class);
  }
  
  @Inject
  public PuzzleDAO(
      final ObjectifyFactory objectifyFactory ) {
    super( objectifyFactory );
  }

  /**
   * Save the passed puxxle information as a new puzzle in the datastore.
   * 
   * @param puzzleInfo Information on the to save in the datastore.
   * @throws ObjectAlreadyCreatedException If the id obtained from {@link PuzzleInfoImpl#getId()} is not {@code null}.
   */
  public void saveNewPuzzleInfo( PuzzleInfoImpl puzzleInfo ) throws ObjectAlreadyCreatedException {

    if( puzzleInfo.getId() != null )
      throw new ObjectAlreadyCreatedException( "Puzzle info already has an id: " + puzzleInfo.getId() );
    
    ofy().put( puzzleInfo );
  }

}
