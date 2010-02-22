package com.puzzlebazar.server.datastore;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.puzzlebazar.shared.model.UserInfo;


@PersistenceCapable
public class UserInfoJdo {

  @PrimaryKey
  @Persistent
  private String email = "";
  @Persistent
  private String firstname = "";
  @Persistent
  private String lastname = "";
  @Persistent
  private String username = "";
  @Persistent
  private boolean administrator = false;

  /**
   * @param destination The UserInfo object to populate with this object content
   */
  public void to( UserInfo destination ) {
    destination.setEmail(email);
    destination.setFirstname(firstname);
    destination.setLastname(lastname);
    destination.setNickname(username);
    destination.setAdministrator(administrator);
  }

  /**
   * @param source The UserInfo object from which to create this object
   */
  public void from( UserInfo source ) {
    email = source.getEmail();
    firstname = source.getFirstname();
    lastname = source.getLastname();
    username = source.getNickname();
    administrator = source.isAdministrator();
  }

  /**
   * @return The email addess of this user
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return The username of this user
   */
  public String getUsername() {
    return username;
  }


}
