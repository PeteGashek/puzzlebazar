package com.puzzlebazar.server.handler;

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

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;


import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.dispatch.server.ActionHandler;
import com.philbeaudoin.gwtp.dispatch.server.ExecutionContext;
import com.philbeaudoin.gwtp.dispatch.shared.ActionException;
import com.puzzlebazar.shared.UserNotFoundException;
import com.puzzlebazar.shared.action.GetPuzzleAction;
import com.puzzlebazar.shared.action.GetPuzzleResult;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzle;

public class GetPuzzleActionHandler implements ActionHandler<GetPuzzleAction, GetPuzzleResult> {

  private final PersistenceManagerFactory persistenceManagerFactory;

  @Inject
  public GetPuzzleActionHandler(
      final PersistenceManagerFactory persistenceManagerFactory ) {
    this.persistenceManagerFactory = persistenceManagerFactory;
  }
  
  @Override
  public GetPuzzleResult execute(GetPuzzleAction action, ExecutionContext context)
      throws ActionException {

    // TODO there should be a separate CreatePuzzleAction
    
    PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
    
    HeyawakePuzzle puzzle = null;
    try {
      Key k = KeyFactory.createKey(HeyawakePuzzle.class.getSimpleName(), action.getId());
      puzzle = persistenceManager.getObjectById( HeyawakePuzzle.class, k );
    }
    catch( JDOObjectNotFoundException exception ) {
      throw new UserNotFoundException();
    }
    finally {
      persistenceManager.close();
    }    
    
    return new GetPuzzleResult(puzzle);
  }

  @Override
  public Class<GetPuzzleAction> getActionType() {
    return GetPuzzleAction.class;
  }

  @Override
  public void undo(GetPuzzleAction action, GetPuzzleResult result,
      ExecutionContext context) throws ActionException {
    // Nothing to undo.
  }


}
