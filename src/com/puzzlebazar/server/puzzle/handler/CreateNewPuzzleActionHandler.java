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

package com.puzzlebazar.server.puzzle.handler;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.actionHandler.ActionHandler;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.puzzlebazar.server.puzzle.heyawake.model.HeyawakeDAO;
import com.puzzlebazar.shared.action.CreateNewPuzzleAction;
import com.puzzlebazar.shared.action.CreateNewPuzzleResult;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzle;

public class CreateNewPuzzleActionHandler implements ActionHandler<CreateNewPuzzleAction, CreateNewPuzzleResult> {

  private final HeyawakeDAO heyawakeDAO;

  @Inject
  public CreateNewPuzzleActionHandler( HeyawakeDAO heyawakeDAO ) {
    this.heyawakeDAO = heyawakeDAO;
  }
  
  @Override
  public CreateNewPuzzleResult execute(CreateNewPuzzleAction action, ExecutionContext context)
      throws ActionException {    

    HeyawakePuzzle puzzle = heyawakeDAO.createNewHeyawake(action.getWidth(), action.getHeight());
    
    return new CreateNewPuzzleResult(puzzle);
  }

  @Override
  public Class<CreateNewPuzzleAction> getActionType() {
    return CreateNewPuzzleAction.class;
  }

  @Override
  public void undo(CreateNewPuzzleAction action, CreateNewPuzzleResult result,
      ExecutionContext context) throws ActionException {
    // TODO Delete puzzle from the datastore
  }


}
