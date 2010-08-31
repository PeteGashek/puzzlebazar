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

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.puzzlebazar.shared.model.UserImpl;

/**
 * This class is used to ensure uniqueness of email addresses. It can also be used to
 * efficiently retrieve a user given its email (without having to do a query).
 * 
 * @author Philippe Beaudoin
 */
public class EmailToUser {

  @SuppressWarnings("unused")
  @Id private String email;
  private Key<UserImpl> userKey;

  @SuppressWarnings("unused")
  private EmailToUser() {
    // For serialization/Objectify only
  }
  
  public EmailToUser(String email, Key<UserImpl> userKey) {
    this.email = email;
    this.userKey = userKey;
  }
  
  public Key<UserImpl> getUserKey() {
    return userKey;
  }

}
