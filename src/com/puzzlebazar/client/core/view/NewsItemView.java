package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.NewsItemPresenter;

public class NewsItemView extends ViewImpl implements NewsItemPresenter.MyView {

  interface Binder extends UiBinder<Widget, NewsItemView> { }
  protected static final Binder binder = GWT.create(Binder.class);  
  
  private final Widget widget;

  @UiField
  Label title;
  
  @Inject
  public NewsItemView() {
    widget = binder.createAndBindUi(this);
  }

  @Override 
  public Widget asWidget() {
    return widget;
  }
  
  @Override
  public void setTitle(String title) {
    this.title.setText( title );
  }

}