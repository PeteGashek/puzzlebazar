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

/**
 * @author Philippe Beaudoin
 */
public interface User extends HasId<UserImpl> {

  int MIN_REALNAME_LENGTH = 1;
  int MIN_NICKNAME_LENGTH = 5;
  int MAX_REALNAME_LENGTH = 30;
  int MAX_NICKNAME_LENGTH = 30;
  int MAX_EMAIL_LENGTH = 80;

  /**
   * @return The email address of this user.
   */
  String getEmail();

  /**
   * @return The real name of this user.
   */
  String getRealName();

  /**
   * @return The nickname of this user.
   */
  String getNickname();

  /**
   * @return The locale for this user. An IEFT language tag.
   */
  String getLocale();

  /**
   * @return True if the user is an administrator.
   */
  boolean isAdministrator();

  /**
   * @param realName The real name to set for this user.
   */
  void setRealname(String realName);

  /**
   * @param nickname The nickname to set for this user.
   */
  void setNickname(String nickname);

  /**
   * @param locale The locale to set for this user. An IEFT language tag.
   */
  void setLocale(String locale);

  /**
   * @return True if the user is authenticade via Google User Service, false if not.
   */
  boolean isAuthenticated();

  /**
   * @return True if the information is valid, false if not
   */
  boolean isValid();

  /**
   * @return An exact copy of this {@link User} object.
   */
  User clone();

}