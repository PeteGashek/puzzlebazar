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
public class EmailToEmail {

  @SuppressWarnings("unused")
  @Id private String email;
  private Key<UserImpl> userKey;

  @SuppressWarnings("unused")
  private EmailToEmail() {
    // For serialization/Objectify only
  }
  
  public EmailToEmail(String email, Key<UserImpl> userKey) {
    this.email = email;
    this.userKey = userKey;
  }
  
  public Key<UserImpl> getUserKey() {
    return userKey;
  }
 
  
  
}
