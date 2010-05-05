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
 * A comment written by a user.
 * 
 * @author Philippe Beaudoin
 */
public interface Comment extends HasId<Comment>, EditableObject, Serializable, HasPrivacySettings {

  /**
   * Access the body this comment.
   * 
   * @return The comment body, a string.
   */
  public String getBody();  
  
  
}
