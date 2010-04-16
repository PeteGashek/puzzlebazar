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

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.puzzlebazar.shared.util.Validation;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class UserImpl implements Serializable, HasKey, User {

  /**
   * 
   */
  private static final long serialVersionUID = -6968794066096789377L;

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
  @Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
  private Long keyId;
  
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


  protected UserImpl() {
    // For serialization only
  }
  
  public UserImpl(String email) {
    this.email = email;
    realName = "";
    nickname = "";
    locale = null;
  }

  public UserImpl(User user) {
    key = user.getKey();
    email = user.getEmail();
    copyEditableFields(user);
  }


  /**
   * Check if the passed user is compatible with this one. If so,
   * copies all the passed user's fields into this one. If not,
   * throws a {@link InvalidEditException}.
   * 
   * @param user The {@link UserImpl} to copy into this one.
   * @throws InvalidEditException Thrown whenever a non-editable field is not the same as this user.
   */
  protected void editFrom(User user) throws InvalidEditException {
    if( !key.equals(user.getKey()) ||
        !email.equals(user.getEmail()) ) 
      throw new InvalidEditException();
    copyEditableFields(user);
  }
  
  @Override
  public String getKey() {
    return key;
  }
  
  @Override
  public long getId() {
    return keyId;
  }
  
  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public String getRealName() {
    return realName;
  }

  @Override
  public String getNickname() {
    return nickname;
  }

  @Override
  public String getLocale() {
    return locale;
  }
  
  @Override
  public boolean isAdministrator() {
    return administrator;
  }

  @Override
  public void setRealname(String realName) {
    this.realName = realName;
  }

  @Override
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
  
  @Override
  public void setLocale(String locale) {
    this.locale = locale;
  }

  protected void setAdministrator(boolean administrator) {
    this.administrator = administrator;
  }

  protected void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }
  
  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public boolean isValid() {		
    return Validation.validateString( realName, MIN_REALNAME_LENGTH, MAX_REALNAME_LENGTH ) &&
    Validation.validateString( email, 0, MAX_EMAIL_LENGTH ) &&
    Validation.validateEmail( email ) &&
    Validation.validateString( nickname, MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH );
  }

  private void copyEditableFields(User user) {
    realName = user.getRealName();
    nickname = user.getNickname();
    locale = user.getLocale();
    authenticated = user.isAuthenticated();
    administrator = user.isAdministrator();
  }
}
