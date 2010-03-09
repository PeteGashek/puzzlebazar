package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.core.presenter.NewsItemPresenter;

public class NewsItemView implements NewsItemPresenter.MyDisplay {

  interface Binder extends UiBinder<Widget, NewsItemView> { }
  protected static final Binder binder = GWT.create(Binder.class);  
  
  private final Widget widget;

  @UiField
  Label title;
  
  @Override public Widget asWidget() {
    return widget;
  }
  
  @Inject
  public NewsItemView() {
    widget = binder.createAndBindUi(this);
  }

  @Override
  public void setTitle(String title) {
    this.title.setText( title );
  }

}