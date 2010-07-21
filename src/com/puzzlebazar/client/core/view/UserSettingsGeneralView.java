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

package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;
import com.puzzlebazar.client.resources.Translations;

public class UserSettingsGeneralView extends ViewImpl implements UserSettingsGeneralPresenter.MyView {

  interface Binder extends UiBinder<Widget, UserSettingsGeneralView> { }
  protected static final Binder binder = GWT.create(Binder.class);
  
  private final Widget widget;
         
  @UiField
  Label email;
  
  @UiField
  TextBox nickname;
  
  @UiField
  Label nicknameError;
  
  @UiField
  TextBox realName;

  @UiField
  Label realNameError;

  @UiField
  ListBox language;

  @UiField
  Button applyButton;

  @UiField
  Button cancelButton;
  
  
  private final FlowPanel executeSuccessMessage = new FlowPanel();
  private final Anchor undoLink;
  
  private final FlowPanel undoSuccessMessage = new FlowPanel();
  private final Anchor redoLink;
  
  @Inject
  public UserSettingsGeneralView( Translations translations ) {
    widget = binder.createAndBindUi(this);
    executeSuccessMessage.add( new InlineLabel( translations.settingsSaved() ) );
    undoLink = new Anchor( translations.undo() );
    executeSuccessMessage.add( undoLink );
    executeSuccessMessage.add( new InlineLabel(".") );
    undoSuccessMessage.add( new InlineLabel( translations.settingsUndone() ) );
    redoLink = new Anchor( translations.redo() );
    undoSuccessMessage.add( redoLink );
    undoSuccessMessage.add( new InlineLabel(".") );
  }
  
  @Override 
  public Widget asWidget() {
    return widget;
  }

  @Override 
  public void addLanguage( String languageName ) {
    this.language.addItem( languageName );
  }
  
  @Override
  public HasText getEmail() {
    return email;
  }

  @Override
  public HasText getNickname() {
    return nickname;
  }

  @Override
  public HasText getRealName() {
    return realName;
  }

  @Override
  public ListBox getLanguage() {
    return language;
  }
  
  @Override
  public void setApplyEnabled(boolean enabled) {
    applyButton.setEnabled(enabled);
  }

  @Override
  public HasClickHandlers getApply() {
    return applyButton;
  }

  @Override
  public HasClickHandlers getCancel() {
    return cancelButton;
  }

  @Override
  public HasClickHandlers getUndo() {
    return undoLink;
  }
  
  @Override
  public HasClickHandlers getRedo() {
    return redoLink;
  }
  
  @Override
  public Widget getExecuteSuccessMessage() { 
    return executeSuccessMessage;
  }
  
  @Override
  public Widget getUndoSuccessMessage() { 
    return undoSuccessMessage;
  }


}
