package com.puzzlebazar.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.presenter.LinkColumnPresenter;

public class LinkColumnView implements LinkColumnPresenter.Display {

  interface Binder extends UiBinder<Widget, LinkColumnView> { }
  private static final Binder binder = GWT.create(Binder.class);

  private final Widget widget;
  
  @Inject
  public LinkColumnView() {
    widget =  binder.createAndBindUi(this);
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

}
