package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.core.presenter.AppPresenter;

public class AppView implements AppPresenter.MyDisplay {

  interface Binder extends UiBinder<Widget, AppView> { }
  protected static final Binder binder = GWT.create(Binder.class);

  private final Widget widget;
  
  @UiField 
  FlowPanel topBarContainer;
  
  @UiField 
  FlowPanel mainContentContainer;
  
  @Inject
  public AppView() {
    widget = binder.createAndBindUi(this);
  }
  
  @Override 
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void setMainContent(Widget mainContent) {
    mainContentContainer.clear();
    mainContentContainer.add( mainContent );
  }

  @Override
  public void setTopBar(Widget topBar) {
    topBarContainer.clear();
    topBarContainer.add( topBar );
  }


}
