package com.puzzlebazar.shared.puzzle.heyawake.model;

import com.googlecode.objectify.Key;
import com.puzzlebazar.shared.puzzle.model.PuzzleDetailsImpl;
import com.puzzlebazar.shared.util.Has2DSize;

public class HeyawakePuzzleDetails extends PuzzleDetailsImpl<HeyawakePuzzleDetails>
implements Has2DSize {

  private static final long serialVersionUID = -598438240782558135L;

  private int width;
  private int height;

  protected HeyawakePuzzleDetails() {
  }
  
  public HeyawakePuzzleDetails(int width, int height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public Key<HeyawakePuzzleDetails> createKey() {
    return new Key<HeyawakePuzzleDetails>(puzzleInfoKey, HeyawakePuzzleDetails.class, getId());
  }

}
