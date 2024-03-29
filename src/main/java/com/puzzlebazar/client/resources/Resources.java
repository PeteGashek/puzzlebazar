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

package com.puzzlebazar.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author Philippe Beaudoin
 */
public interface Resources extends ClientBundle {

  @Source("logo.png")
  ImageResource logo();

  @Source("Puzzlebazar.css")
  Style style();

  @Source("defines.css")
  Defines defines();

  /**
   * @author Philippe Beaudoin
   */
  public interface Style extends CssResource {
    String username();
    String formMargin();
    String errorText();
    String round1();
    String round2();
    String round4();
    String gray();
    String black();
    String yellow();
    String blue();
    String transparent();
    String vertex();
    String edge();
    String cell();
    String selectedCell();
  }

  /**
   * @author Philippe Beaudoin
   */
  public interface Defines extends CssResource {
    String darkPanelColor();
    String lightPanelColor();
    String titleFontWeight();
    String titleFontSize();
    String titleColor();
    String formLineSpacing();
  }

}
