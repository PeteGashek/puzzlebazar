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

package com.puzzlebazar.server.puzzle.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.puzzlebazar.shared.puzzle.model.PuzzleInfoImpl;

/**
 * The server version of {@link PuzzleInfoImpl}. This class 
 * stores keys to linked objects and offers fetch methods 
 * to get them from the datastore or the cache.
 * 
 * @author Philippe Beaudoin
 */
@PersistenceCapable
public class PuzzleInfoImplServer extends PuzzleInfoImpl {

  private static final long serialVersionUID = 4832076911208632555L;

  // Polymorphic relationship, we keep the key instead of the object
  @Persistent
  private String puzzleDetailsKey;

  // Unowned relationship, we keep the key instead of the object
  @Persistent
  private String authorKey;
  
  // Unowned relationship, we keep the key instead of the object
  @Persistent
  private String puzzleTypeKey;

  
}
