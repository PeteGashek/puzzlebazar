package com.puzzlebazar.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.presenter.TopBarPresenter;
import com.puzzlebazar.client.resources.Resources;

public class TopBarView implements TopBarPresenter.Display {

  interface Binder extends UiBinder<Widget, TopBarView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField(provided = true)
  final Resources resources;
  
  private final Widget widget;
  
  @Inject
  public TopBarView( Resources resources ) {
    this.resources = resources;
    widget =  binder.createAndBindUi(this);
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

}
