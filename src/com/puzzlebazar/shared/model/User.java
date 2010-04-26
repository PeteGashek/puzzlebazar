package com.puzzlebazar.shared.model;

import com.googlecode.objectify.Key;

public interface User {

  public static final int MIN_REALNAME_LENGTH = 1;
  public static final int MIN_NICKNAME_LENGTH = 5;
  public static final int MAX_REALNAME_LENGTH = 30;
  public static final int MAX_NICKNAME_LENGTH = 30;
  public static final int MAX_EMAIL_LENGTH = 80;

  /**
   * @return The id of this user.
   */
  public long getId();

  /**
   * Create a new Key that uniquely identifies this user.
   * 
   * @return The newly created key.
   */
  public Key<UserImpl> createKey();
  
  /**
   * @return The email address of this user. 
   */
  public String getEmail();

  /**
   * @return The real name of this user. 
   */
  public String getRealName();

  /**
   * @return The nickname of this user.
   */
  public String getNickname();

  /**
   * @return The locale for this user. An IEFT language tag.
   */
  public String getLocale();

  /**
   * @return True if the user is an administrator.
   */
  public boolean isAdministrator();

  /**
   * @param realname The real name to set for this user.
   */
  public void setRealname(String realName);

  /**
   * @param nickname The nickname to set for this user.
   */
  public void setNickname(String nickname);

  /**
   * @param locale The locale to set for this user. An IEFT language tag. 
   */
  public void setLocale(String locale);

  /**
   * @return True if the user is authenticade via Google User Service, false if not.
   */
  public boolean isAuthenticated();

  /**
   * @return True if the information is valid, false if not
   */
  public boolean isValid();


}