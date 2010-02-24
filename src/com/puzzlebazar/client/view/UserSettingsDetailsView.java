package com.puzzlebazar.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.presenter.UserSettingsDetailsPresenter;

public class UserSettingsDetailsView implements UserSettingsDetailsPresenter.Display {

  interface Binder extends UiBinder<Widget, UserSettingsDetailsView> { }
  private static final Binder binder = GWT.create(Binder.class);

  private final Widget widget;
  
  @Inject
  public UserSettingsDetailsView() {
    widget =  binder.createAndBindUi(this);
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

}
