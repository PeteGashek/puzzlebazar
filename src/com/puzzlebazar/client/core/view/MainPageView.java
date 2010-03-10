package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;

public class MainPageView implements MainPagePresenter.MyView {

  interface Binder extends UiBinder<Widget, MainPageView> { }
  protected static final Binder binder = GWT.create(Binder.class);
  
  private final Widget widget;
  
  @UiField
  FlowPanel newsPanel;
  
  @Inject
  public MainPageView() {
    widget = binder.createAndBindUi(this);
  }
  
  @Override 
  public Widget asWidget() {
    return widget;
  }
  
  @Override
  public void addNewsWidget(Widget widget) {
    newsPanel.add( widget );
  }

  @Override
  public void clearNewsWidgets() {
    newsPanel.clear();
  }

}
