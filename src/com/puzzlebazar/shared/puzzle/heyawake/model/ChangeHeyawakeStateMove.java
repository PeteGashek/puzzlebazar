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

package com.puzzlebazar.shared.puzzle.heyawake.model;

import com.puzzlebazar.shared.puzzle.squaregrid.model.ChangeCellStateMove;
import com.puzzlebazar.shared.util.Vec2i;

public class ChangeHeyawakeStateMove extends ChangeCellStateMove<HeyawakeCellState> 
implements HeyawakeMove {

  private static final long serialVersionUID = 5629287498654423276L;


  @SuppressWarnings("unused")
  private ChangeHeyawakeStateMove() {
    // For serialization only
    super();
  }


  /**
   * Creates a move to indicate a change of state inside a {@link HeyawakePuzzle}.
   * Pass both the state before the change and the state after the change,
   * making it possible to undo this event.
   * 
   * @param loc The location of the cell on which to change state. A {@link Vec2i}.
   * @param stateBefore The {@link HeyawakeCellState} before the change.
   * @param stateAfter  The {@link HeyawakeCellState} after the change.
   */
  public ChangeHeyawakeStateMove(
      Vec2i loc,
      HeyawakeCellState stateBefore,
      HeyawakeCellState stateAfter) {
    super(loc, stateBefore, stateAfter);
  }


  @Override
  public void doMove(HeyawakePuzzle puzzle) {
    puzzle.setCellState(getLoc(), getStateAfter());
  }


  @Override
  public void undoMove(HeyawakePuzzle puzzle) {
    puzzle.setCellState(getLoc(), getStateBefore());
  }

}
