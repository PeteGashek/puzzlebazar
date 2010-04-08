package com.puzzlebazar.shared.puzzle.heyawake.model;

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


import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.puzzlebazar.shared.util.Has2DSize;
import com.puzzlebazar.shared.util.HasKey;

@PersistenceCapable
public class HeyawakePuzzleInfo implements HasKey, Has2DSize {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
  private String key;
  
  @Persistent(mappedBy = "puzzleInfo")
  private HeyawakePuzzle puzzle;  

  @Persistent
  private int width;
  
  @Persistent
  private int height;
  
  @Override
  public String getKey() {
    return key;
  }

  @SuppressWarnings("unused")
  private HeyawakePuzzleInfo() {
    // For serialization only    
  }
  
  public HeyawakePuzzleInfo( int width, int height ) {
    this.width = width;
    this.height = height;
  }
  
  /**
   * Access the puzzle associated with this {@link HeyawakePuzzleInfo}.
   * 
   * @return The associated {@link HeyawakePuzzle}.
   */
  public HeyawakePuzzle getPuzzle() {
    return puzzle;
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
