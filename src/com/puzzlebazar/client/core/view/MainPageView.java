package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;

public class MainPageView implements MainPagePresenter.Display {

  interface Binder extends UiBinder<Widget, MainPageView> { }
  protected static final Binder binder = GWT.create(Binder.class);
  private final Widget widget;
  @Override public Widget asWidget() {
    return widget;
  }
  
  @Inject
  public MainPageView() {
    widget = binder.createAndBindUi(this);
  }

}
