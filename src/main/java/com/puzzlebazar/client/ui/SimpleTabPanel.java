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

package com.puzzlebazar.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Tab;
import com.puzzlebazar.client.resources.Resources;

/**
 * A widget that can show multiple tabs with rounded corners, each with its own content.
 *
 * @author Philippe Beaudoin
 */
public class SimpleTabPanel extends BaseTabPanel {

  interface Binder extends UiBinder<Widget, SimpleTabPanel> { }
  private static final Binder binder = GWT.create(Binder.class);

  @Inject
  static Resources resources;

  @UiField
  Label title;

  /**
   * Creates a panel a title and simple tabs under it.
   *
   * @param title The title to display on the panel
   */
  @UiConstructor
  public SimpleTabPanel(String title) {
    initWidget(binder.createAndBindUi(this));
    this.title.setText(title);
  }

  @UiFactory
  public static Resources getResources() {
    return resources;
  }

  @Override
  protected Tab createNewTab(float priority) {
    return new SimpleTab(priority);
  }

}
