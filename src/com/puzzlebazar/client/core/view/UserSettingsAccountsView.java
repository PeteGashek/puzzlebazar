package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.core.presenter.UserSettingsAccountsPresenter;

public class UserSettingsAccountsView implements UserSettingsAccountsPresenter.MyView {

  interface Binder extends UiBinder<Widget, UserSettingsAccountsView> { }
  protected static final Binder binder = GWT.create(Binder.class);
  
  private final Widget widget;
  
  @Inject
  public UserSettingsAccountsView() {
    widget = binder.createAndBindUi(this);
  }

  @Override 
  public Widget asWidget() {
    return widget;
  }
}
