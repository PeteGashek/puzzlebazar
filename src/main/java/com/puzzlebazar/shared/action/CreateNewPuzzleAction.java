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

package com.puzzlebazar.shared.action;

import com.gwtplatform.dispatch.shared.ActionImpl;

/**
 * @author Philippe Beaudoin
 */
public class CreateNewPuzzleAction extends ActionImpl< CreateNewPuzzleResult > {

  // TODO Should provide the desired puzzle type, for now we default to HeyawakePuzzle
  private String title;
  private int width;
  private int height;

  @SuppressWarnings("unused")
  private CreateNewPuzzleAction() {
    // For serialization only
  }

  public CreateNewPuzzleAction(String title, int width, int height) {
    this.title = title;
    this.width = width;
    this.height = height;
  }

  public String getTitle() {
    return title;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

}
