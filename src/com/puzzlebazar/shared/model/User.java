package com.puzzlebazar.shared.model;

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

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.puzzlebazar.shared.util.HasKey;
import com.puzzlebazar.shared.util.Validation;

@PersistenceCapable
public class User implements Serializable, HasKey {

  /**
   * 
   */
  private static final long serialVersionUID = -6968794066096789377L;

  public static final int MIN_REALNAME_LENGTH = 1;
  public static final int MIN_NICKNAME_LENGTH = 5;

  public static final int MAX_REALNAME_LENGTH = 30;
  public static final int MAX_NICKNAME_LENGTH = 30;
  public static final int MAX_EMAIL_LENGTH = 80;

  public class ErrorFlags {
    public static final int SUCCESS = 0;
    public static final int EMAIL_ALREADY_REGISTERED = 0x0001;
    public static final int UNREGISTERED_EMAIL = 0x0100;
    public static final int NICKNAME_MODIFIED = 0x1000;
    public static final int MODIFYING_OTHER_USER = 0x2000;
    public static final int UNKOWN_ERROR = 0x8000;
  };


  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
  private String key;

  @Persistent
  private String email;
  @Persistent
  private String realName;
  @Persistent
  private String nickname;
  @Persistent
  private String locale; // null means default locale
  private boolean administrator = false;
  private boolean authenticated = false;


  @SuppressWarnings("unused")
  private User() {
    // For serialization only
  }
  
  public User(String email) {
    this.email = email;
    realName = "";
    nickname = "";
    locale = null;
  }

  public User(User user) {
    key = user.key;
    email = user.email;
    realName = user.realName;
    nickname = user.nickname;
    locale = user.locale;
  }

  /**
   * Check if the passed user is compatible with this one. If so,
   * copies all the passed user's fields into this one. If not,
   * throws a {@link InvalidEditException}.
   * 
   * @param user The {@link User} to copy into this one.
   * @throws InvalidEditException Thrown whenever the passed user's key or email is not the same as this user.
   */
  public void editFrom(User user) throws InvalidEditException {
    if( !key.equals(user.key) ||
        !email.equals(user.email) ) 
      throw new InvalidEditException();
    
    this.key = user.key;
    this.email = user.email;
    this.realName = user.realName;
    this.nickname = user.nickname;
    this.locale = user.locale;
  }
  
  public String getKey() {
    return key;
  }
  
  /**
   * @return The email address of this user. 
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return The real name of this user. 
   */
  public String getRealName() {
    return realName;
  }

  /**
   * @return The nickname of this user.
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * @return The locale for this user. An IEFT language tag.
   */
  public String getLocale() {
    return locale;
  }
  
  /**
   * @return True if the user is an administrator.
   */
  public boolean isAdministrator() {
    return administrator;
  }

  /**
   * @param realname The real name to set for this user.
   */
  public void setRealname(String realName) {
    this.realName = realName;
  }

  /**
   * @param nickname The nickname to set for this user.
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
  
  /**
   * @param locale The locale to set for this user. An IEFT language tag. 
   */
  public void setLocale(String locale) {
    this.locale = locale;
  }

  /**
   * @param administrator True if the user should be an administrator, false otherwise.
   */
  public void setAdministrator(boolean administrator) {
    this.administrator = administrator;
  }

  /**
   * @param authenticated True if the user is authenticated via Google User Service, false if not.
   */
  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }
  
  /**
   * @return True if the user is authenticade via Google User Service, false if not.
   */
  public boolean isAuthenticated() {
    return authenticated;
  }

  /**
   * @return True if the information is valid, false if not
   */
  public boolean isValid() {		
    return Validation.validateString( realName, MIN_REALNAME_LENGTH, MAX_REALNAME_LENGTH ) &&
    Validation.validateString( email, 0, MAX_EMAIL_LENGTH ) &&
    Validation.validateEmail( email ) &&
    Validation.validateString( nickname, MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH );
  }


}
