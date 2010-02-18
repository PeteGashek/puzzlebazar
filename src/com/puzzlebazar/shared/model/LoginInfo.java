package com.puzzlebazar.shared.model;

import java.io.Serializable;

public class LoginInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7937446280553489354L;
  
  
  private String username = null;
  private String useragent = null;
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUseragent(String useragent) {
    this.useragent = useragent;
  }
  
  public String getUseragent() {
    return useragent;
  }



}
