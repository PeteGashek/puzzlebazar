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

package com.puzzlebazar.client.util;

/**
 * The interface of an object that can react to changes
 * detected within another object, or a collection of other
 * objects.
 * 
 * @see ChangeMonitorUnit
 * @see ChangeMonitorImpl
 * 
 * @author Philippe Beaudoin
 */
public interface MonitorHandler {
  
  /**
   * Called whenever the associated object has changed.
   */
  public void changeDetected();
  
  /**
   * Called whenever all changes in the associated object 
   * have been reverted.
   */
  public void changeReverted();
}