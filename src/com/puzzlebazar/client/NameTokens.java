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

package com.puzzlebazar.client;

import com.philbeaudoin.gwtp.mvp.client.annotations.NameToken;

/**
 * The central location of all name tokens for the application.
 * All {@link ProxyPlace} classes get their tokens from here.
 * This class also makes it easy to use name tokens as
 * a resource within UIBinder xml files. 
 * <p />
 * This class uses both static variables and static getters.
 * The reason for this is that, if you want to use {@code NameTokens}
 * in a UiBinder file, you can only access static methods of the class.
 * On the other hand, when you use the {@literal @}{@link NameToken} 
 * annotation, you can only refer to a static variable.
 *
 * @author Philippe Beaudoin
 */
public class NameTokens {


  public static final String adminGeneral = "!admin";
  public static String getAdminGeneral() {
    return adminGeneral;
  }

  public static final String adminUsers = "!admin-users";
  public static String getAdminUsers() {
    return adminUsers;
  }

  public static final String userSettingsGeneral = "!settings";
  public static String getUserSettingsGeneral() {
    return userSettingsGeneral;
  }

  public static final String userSettingsAccounts = "!settings-accounts";
  public static String getUserSettingsAccounts() {
    return userSettingsAccounts;
  }
  
  public static final String mainPage = "!main";
  public static String getMainPage() {
    return mainPage;
  }

  public static final String puzzlePage = "!puzzle";
  public static String getPuzzlePage() {
    // TODO Temp, just for testing. This shouldn't be a place.
    return puzzlePage;
  }
  
}
