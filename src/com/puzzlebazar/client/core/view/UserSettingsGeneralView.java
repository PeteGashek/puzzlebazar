package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;

public class UserSettingsGeneralView implements UserSettingsGeneralPresenter.MyView {

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
