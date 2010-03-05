package com.puzzlebazar.client;

/**
 * The central location of all name tokens for the application.
 * All {@link ProxyPlace} classes get their tokens from here.
 * This class also makes it easy to use name tokens as
 * a resource within UIBinder xml files. 
 *
 * @author Philippe Beaudoin
 */
public class NameTokens {

  public static String getAdminGeneral() {
    return "admin";
  }

  public static String getAdminUsers() {
    return "admin-users";
  }

  public static String getUserSettingsGeneral() {
    return "settings";
  }

  public static String getUserSettingsAccounts() {
    return "settings-accounts";
  }
  
  public static String getMainPage() {
    return "main";
  }
  
}
