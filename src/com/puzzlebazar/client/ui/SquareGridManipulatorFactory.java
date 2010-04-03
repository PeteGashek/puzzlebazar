package com.puzzlebazar.client.ui;

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


import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.user.client.ui.Widget;

/**
 * The factory class to produce {@link SquareGridManipulatorImpl} objects.
 * 
 * @author Philippe Beaudoin
 */
public interface SquareGridManipulatorFactory {

  /**
   * Creates a {@link SquareGridManipulator} attached to a panel 
   * and a UI widget.
   * 
   * @param gridPanel The {@link SquareGridLayoutPanel} on which to add a manipulator.
   * @param uiWidget The {@link Widget} receiving the ui input. Must implement
   *   the following interfaces: {@link HasMouseDownHandlers},
   *   {@link HasMouseUpHandlers}, {@link HasMouseMouveHandlers},
   *   {@link HasMouseOutHandlers}.
   */  
  public SquareGridManipulator create(
      SquareGridLayoutPanel gridPanel,
      Widget uiWidget );
}
