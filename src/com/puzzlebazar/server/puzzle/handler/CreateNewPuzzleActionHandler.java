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
import com.philbeaudoin.gwtp.dispatch.server.ActionHandler;
import com.philbeaudoin.gwtp.dispatch.server.ExecutionContext;
import com.philbeaudoin.gwtp.dispatch.shared.ActionException;
import com.puzzlebazar.server.puzzle.model.PuzzleDAO;
import com.puzzlebazar.shared.action.CreateNewPuzzleAction;
import com.puzzlebazar.shared.action.CreateNewPuzzleResult;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzle;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzleDetails;

public class CreateNewPuzzleActionHandler implements ActionHandler<CreateNewPuzzleAction, CreateNewPuzzleResult> {

  private final PuzzleDAO puzzleDAO;

  @Inject
  public CreateNewPuzzleActionHandler( PuzzleDAO puzzleDAO ) {
    this.puzzleDAO = puzzleDAO;
  }
  
  @Override
  public CreateNewPuzzleResult execute(CreateNewPuzzleAction action, ExecutionContext context)
      throws ActionException {

    // TODO there should be a separate CreatePuzzleAction
    
    // TODO this is not done yet...
    HeyawakePuzzleDetails puzzleDetails = new HeyawakePuzzleDetails(action.getWidth(), action.getHeight());
    HeyawakePuzzle puzzle = new HeyawakePuzzle();
    puzzle.setDetails( puzzleDetails );
    
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
