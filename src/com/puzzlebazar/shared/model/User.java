package com.puzzlebazar.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.puzzlebazar.shared.util.Validation;

@PersistenceCapable
public class User implements Serializable {

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

  /**
   * @return This object's key, encoded as a string.
   */
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
   * @return The locale for this user.
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
   * @param locale The locale to set for this user.
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
