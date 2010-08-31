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

package com.puzzlebazar.server.model;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.puzzlebazar.shared.model.UserImpl;

/**
 * Stores information on the current session.
 * 
 * @author Philippe Beaudoin
 */
@Cached
public class Session implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1433528124645869019L;

  @SuppressWarnings("unused")
  @Id private String sessionId;
  private Key<UserImpl> currentUserKey;

  @SuppressWarnings("unused")
  private Session() {
    // For serialization/Objectify only
  }
  
  public Session(String sessionId, Key<UserImpl> currentUserKey) {
    this.sessionId = sessionId;
    this.currentUserKey = currentUserKey;
  }

  /**
   * @return The attached {@link UserImpl}'s key.
   */
  public Key<UserImpl> getCurrentUserKey() {
    return currentUserKey;
  }

  /**
   * Logout the user from the current session. This is done by setting the
   * {@code currentUserKey} to {@code null}.
   */
  public void logoutUser() {
    currentUserKey = null;
  }

}
