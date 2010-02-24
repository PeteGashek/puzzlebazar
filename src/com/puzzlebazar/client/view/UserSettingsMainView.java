package com.puzzlebazar.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.presenter.UserSettingsMainPresenter;

public class UserSettingsMainView implements UserSettingsMainPresenter.Display {

  interface Binder extends UiBinder<Widget, UserSettingsMainView> { }
  private static final Binder binder = GWT.create(Binder.class);

  private final Widget widget;
  
  @Inject
  public UserSettingsMainView() {
    widget =  binder.createAndBindUi(this);
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

}
