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


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.resources.Resources;

/**
 * A centered message box containing a one-line message.
 * 
 * @author Philippe Beaudoin
 */
public class ShortMessageBox extends Composite implements HasText {

  interface Binder extends UiBinder<Widget, ShortMessageBox> { }
  private static final Binder binder = GWT.create(Binder.class);

  @Inject 
  static Resources resources;

  @UiField
  HTMLPanel messageBox;
  
  @UiField
  Label message;

  public ShortMessageBox() {
    super();
    initWidget( binder.createAndBindUi( this ) );
    clearText();
  }

  @UiFactory
  public static Resources getResources() {
    return resources;
  }

  @Override
  public String getText() {
    return message.getText();
  }

  @Override
  public void setText(String text) {
    if( text == null || text.length() == 0 )
      clearText();
    else {
      message.setText( text );
      messageBox.setVisible(true);
    }
  }

  public void clearText() {
    message.setText("");
    messageBox.setVisible(false);
  }
}
