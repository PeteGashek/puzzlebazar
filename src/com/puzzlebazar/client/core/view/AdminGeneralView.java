package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.core.presenter.AdminGeneralPresenter;

public class AdminGeneralView implements AdminGeneralPresenter.Display {

  interface Binder extends UiBinder<Widget, AdminGeneralView> { }
  protected static final Binder binder = GWT.create(Binder.class);
  private final Widget widget;
  @Override public Widget asWidget() {
    return widget;
  }
  
  @Inject
  public AdminGeneralView() {
    widget = binder.createAndBindUi(this);
  }

}
