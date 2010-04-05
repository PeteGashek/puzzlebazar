package com.puzzlebazar.shared.puzzle.squaregrid.model;

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

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.gwtp.dispatch.shared.Action;
import com.puzzlebazar.shared.action.NoResult;
import com.puzzlebazar.shared.util.HasKey;

public class ChangeCellStateEvent extends GwtEvent<ChangeCellStateHandler> implements Action<NoResult>, HasKey {

  private static final long serialVersionUID = 8586160813194830717L;
  
  private static final Type<ChangeCellStateHandler> TYPE = new Type<ChangeCellStateHandler>();

  public static Type<ChangeCellStateHandler> getType() {
    return TYPE;
  }

  private String puzzleKey;
  // TODO state should be an object
  private int stateBefore;
  private int stateAfter;

  @SuppressWarnings("unused")
  private ChangeCellStateEvent() {
    // For serialization only
    puzzleKey = null;
    stateBefore = stateAfter = -1;
  }

  /**
   * Creates an event to indicate a change of state inside a {@link CellArray}.
   * Pass both the state before the change and the state after the change,
   * making it possible to undo this event.
   * 
   * @param puzzleKey The key of the associated puzzle.
   * @param stateBefore The state before the change.
   * @param stateAfter  The state after the change.
   */
  public ChangeCellStateEvent( String puzzleKey, int stateBefore, int stateAfter ) {
    this.stateBefore = stateBefore; 
    this.stateAfter = stateAfter;
  }

  @Override
  public String getKey() {
    return puzzleKey;
  }
  
  /**
   * @return The state that was in the cell before the change.
   */
  public int getStateBefore() {
    return stateBefore;
  }

  /**
   * @return The state that should be in the cell after the change.
   */
  public int getStateAfter() {
    return stateAfter;
  }
  
  @Override
  protected void dispatch(ChangeCellStateHandler handler) {
    handler.onChangeCellState( this );
  }

  @Override
  public Type<ChangeCellStateHandler> getAssociatedType() {
    // TODO Auto-generated method stub
    return getType();
  }


}
