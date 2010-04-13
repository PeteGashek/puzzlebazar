package com.puzzlebazar.shared.model;

public interface User {

  public static final int MIN_REALNAME_LENGTH = 1;
  public static final int MIN_NICKNAME_LENGTH = 5;
  public static final int MAX_REALNAME_LENGTH = 30;
  public static final int MAX_NICKNAME_LENGTH = 30;
  public static final int MAX_EMAIL_LENGTH = 80;

  public abstract String getKey();

  public abstract long getId();

  /**
   * @return The email address of this user. 
   */
  public abstract String getEmail();

  /**
   * @return The real name of this user. 
   */
  public abstract String getRealName();

  /**
   * @return The nickname of this user.
   */
  public abstract String getNickname();

  /**
   * @return The locale for this user. An IEFT language tag.
   */
  public abstract String getLocale();

  /**
   * @return True if the user is an administrator.
   */
  public abstract boolean isAdministrator();

  /**
   * @param realname The real name to set for this user.
   */
  public abstract void setRealname(String realName);

  /**
   * @param nickname The nickname to set for this user.
   */
  public abstract void setNickname(String nickname);

  /**
   * @param locale The locale to set for this user. An IEFT language tag. 
   */
  public abstract void setLocale(String locale);

  /**
   * @return True if the user is authenticade via Google User Service, false if not.
   */
  public abstract boolean isAuthenticated();

  /**
   * @return True if the information is valid, false if not
   */
  public abstract boolean isValid();

}