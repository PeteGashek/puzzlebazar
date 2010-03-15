package com.puzzlebazar.server.model;

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
  


}
