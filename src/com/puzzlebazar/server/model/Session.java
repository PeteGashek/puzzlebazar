package com.puzzlebazar.server.model;

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


import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


import com.puzzlebazar.shared.model.User;

/**
 * Stores information on the current session.
 * 
 * @author Philippe Beaudoin
 */
@PersistenceCapable
public class Session implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1433528124645869019L;

  @SuppressWarnings("unused")
  @PrimaryKey
  @Persistent
  private String decoratedSessionId;

  @Persistent
  private String currentUserKey;

  @SuppressWarnings("unused")
  private Session() {
    // For serialization only
  }
  
  public Session(String decoratedSessionId, String currentUserKey) {
    this.decoratedSessionId = decoratedSessionId;
    this.currentUserKey = currentUserKey;
  }

  /**
   * @return The attached {@link User}'s key, encoded as a string.
   */
  public String getCurrentUserKey() {
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
