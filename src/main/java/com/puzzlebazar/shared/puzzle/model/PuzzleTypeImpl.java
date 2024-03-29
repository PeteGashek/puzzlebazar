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

package com.puzzlebazar.shared.puzzle.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

/**
 * @author Philippe Beaudoin
 */
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
