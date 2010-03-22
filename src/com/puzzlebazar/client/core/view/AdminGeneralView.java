package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.AdminGeneralPresenter;

public class AdminGeneralView extends ViewImpl implements AdminGeneralPresenter.MyView {

  interface Binder extends UiBinder<Widget, AdminGeneralView> { }
  protected static final Binder binder = GWT.create(Binder.class);

  private final Widget widget;
  
  @Inject
  public AdminGeneralView() {
    widget = binder.createAndBindUi(this);
  }

  @Override 
  public Widget asWidget() {
    return widget;
  }
  

}
