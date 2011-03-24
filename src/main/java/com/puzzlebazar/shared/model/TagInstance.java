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

import java.io.Serializable;

/**
 * A specific use of a {@link Tag} attached to a given object.
 *
 * @author Philippe Beaudoin
 */
public interface TagInstance extends Serializable {

  /**
   * Get the {@link Tag}.
   *
   * @return The {@link Tag}.
   */
  Tag getTag();

  /**
   * Get the total number of users who attached this tag to the object.
   *
   * @return The number of users who attached this tag to the object.
   */
  int getNumberOfTaggers();

  /**
   * @return The list of all users who attached this tag to the object.
   */
  User getTaggers();

  void tag(User user);
  void untag(User user);

}
