package com.puzzlebazar.shared.puzzle.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class PuzzleTypeImpl implements PuzzleType {

  private static final long serialVersionUID = 3032269804933268835L;

  @Id Long id;
  
  @Override
  public String getSimpleName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public Key<PuzzleTypeImpl> createKey() {
    return new Key<PuzzleTypeImpl>(PuzzleTypeImpl.class, id);
  }

}
