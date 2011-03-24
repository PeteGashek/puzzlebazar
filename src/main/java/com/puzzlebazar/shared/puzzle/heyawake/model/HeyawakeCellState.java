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

import com.puzzlebazar.shared.puzzle.squaregrid.model.CellState;

/**
 * The state of an heyawake cell.
 *
 * @author Philippe Beaudoin
 */
public class HeyawakeCellState implements CellState {

  private static final long serialVersionUID = -4133591514717465517L;

  public static final int UNKNOWN = 0;
  public static final int EMPTY = 1;
  public static final int FILLED = 1;

  private int content;

  /**
   * Creates an {@link #UNKNOWN} heyawake cell state.
   */
  public HeyawakeCellState() {
    content = UNKNOWN;
  }

  /**
   * Creates an heyawake cell state with the specified content.
   *
   * @param content The desired content. One of {@link #UNKNOWN},
   * {@link #EMPTY} or {@link @FILLED}.
   */
  public HeyawakeCellState(int content) {
    setContent(content);
  }

  /**
   * Retrieves the state of the cell. One of {@link #UNKNOWN},
   * {@link #EMPTY} or {@link @FILLED}.
   *
   * @return The state, an int.
   */
  public int getContent() {
    return content;
  }

  /**
   * Sets the content of the heyawake cell state.
   *
   * @param content The desired content. One of {@link #UNKNOWN},
   * {@link #EMPTY} or {@link @FILLED}.
   */
  public void setContent(int content) {
    assert content == UNKNOWN || content == EMPTY || content == FILLED :
      "Invalid content in HeyawakeCellState.";
    this.content = content;
  }
}
