package com.puzzlebazar.shared.model;

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

import java.io.Serializable;
import java.util.List;

/**
 * Information has to whether or not a user has the rights needed
 * to perform a given action.
 * 
 * @author Philippe Beaudoin
 */
public interface ActionRightsInfo extends Serializable {
  
  /**
   * Checks whether or not the user has the rights to perform an action.
   * 
   * @return {@code true} if the user has the rights to perform the action.
   */
  public boolean canPerformAction();  

  /**
   * Returns the name of this action. This is useful for displaying error messages.
   * 
   * @return The name of this action, a String.
   */
  public String getActionName();
  
  /**
   * Indicates the warning message that should be displayed to the user before
   * letting him perform the action. If this method returns {@code null} then no
   * warning is required.
   * <p />
   * A warning should be issued when a user tries to perform an action that is 
   * unusual for him. For example, the administrator has a lot of rights, but some
   * actions should rarely be performed. 
   * 
   * @return The warning message, or {@code null} if no warning is necessary.
   */
  public String getWarning();  


  /**
   * List all the rights that are required in order to perform that action.
   * 
   * @return The list of all required {@link Right}.
   */  
  public List<Right> getNeededRights();
  
  /**
   * List all the rights that the user requires in order to perform that action.
   * If this method returns an empty list, then {@link #canPerformAction()} should
   * return {@code true}.
   * 
   * @return The list of missing {@link Right}.
   */
  public List<Right> getMissingRights();
  
}
