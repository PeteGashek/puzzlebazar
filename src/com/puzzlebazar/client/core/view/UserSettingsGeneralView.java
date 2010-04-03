package com.puzzlebazar.client.core.view;

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
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;

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
  
  @Inject
  public UserSettingsGeneralView() {
    widget = binder.createAndBindUi(this);    
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


}
