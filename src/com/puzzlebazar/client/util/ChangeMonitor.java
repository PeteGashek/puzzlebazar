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

import com.gwtplatform.mvp.client.HandlerContainer;

/**
 * Classes of this type contains a collection of {@link ChangeMonitorUnit}
 * objects. It fires {@link #changeDetected()} once as soon as one 
 * object of the collection changes value. It fires 
 * {@link #changeReverted()} as soon as all the objects in the collection
 * recover their original value.
 * 
 * @author Philippe Beaudoin
 */
public interface ChangeMonitor extends MonitorHandler, HandlerContainer {

  /**
   * Indicates a handler that will be called whenever a
   * change is detected.
   * 
   * @param handler The {@link MonitorHandler} to call.
   */
  void setHandler(MonitorHandler handler);

  /**
   * Starts monitoring changes on a new widget. This object will be
   * monitored until {@link #unbind()} is called.
   * 
   * @param widget The object to monitor. The object will be tested
   *               for a number of supported types and the change 
   *               monitor will adapt to the type. 
   */
  public abstract void monitorWidget(Object widget);

  /**
   * Resets all the original values to match the values currently contained
   * in the widget. Will notify the associated {@link MonitorHandler}.
   */
  void reset();

}