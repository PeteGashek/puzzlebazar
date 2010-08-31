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

package com.puzzlebazar.shared.model;

import com.googlecode.objectify.Key;

/**
 * Every object stored in the datastore with an automatic id should
 * implement this interface.
 * 
 * @param <T> The specific implementation type.
 * 
 * @author Philippe Beaudoin
 */
public interface HasId<T> {

  /**
   * @return The id of this user.
   */
  Long getId();

  /**
   * Create a new Key that uniquely identifies this object.
   * 
   * @return The newly created key.
   */
  Key<T> createKey();
  
}
