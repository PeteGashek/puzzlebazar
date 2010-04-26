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

import java.io.Serializable;

import javax.persistence.Id;

import com.puzzlebazar.shared.util.Has2DSize;

public class HeyawakePuzzleInfo implements Has2DSize, Serializable {

  private static final long serialVersionUID = -7101218761822467018L;

  @Id private Long id;
  
  private HeyawakePuzzle puzzle;  

  private String title;
  private int width;
  private int height;
  

  @SuppressWarnings("unused")
  private HeyawakePuzzleInfo() {
    // For serialization only    
  }
  
  public HeyawakePuzzleInfo( String title, int width, int height ) {
    this.title = title;
    this.width = width;
    this.height = height;
  }

  public long getId() {
    return id;
  }
  
  /**
   * Access the puzzle associated with this {@link HeyawakePuzzleInfo}.
   * 
   * @return The associated {@link HeyawakePuzzle}.
   */
  public HeyawakePuzzle getPuzzle() {
    return puzzle;
  }
  
  public String getTitle() {
    return title;
  }
  
  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

}
