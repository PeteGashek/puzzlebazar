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
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.puzzlebazar.shared.action.GetPuzzleAction;
import com.puzzlebazar.shared.action.GetPuzzleResult;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzle;

public class GetPuzzleActionHandler implements ActionHandler<GetPuzzleAction, GetPuzzleResult> {

  @Inject
  public GetPuzzleActionHandler() {
  }
  
  @Override
  public GetPuzzleResult execute(GetPuzzleAction action, ExecutionContext context)
      throws ActionException {

    // TODO there should be a separate CreatePuzzleAction
    HeyawakePuzzle puzzle = null;
    
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
