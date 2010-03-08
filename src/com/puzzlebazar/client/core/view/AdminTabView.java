package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.Tab;
import com.puzzlebazar.client.core.presenter.AdminTabPresenter;
import com.puzzlebazar.client.ui.SimpleTabPanel;

public class AdminTabView implements AdminTabPresenter.MyDisplay {
  
  interface Binder extends UiBinder<Widget, AdminTabView> { }
  protected static final Binder binder = GWT.create(Binder.class);
  private final Widget widget;
  @Override public Widget asWidget() {
    return widget;
  }

  @UiField
  SimpleTabPanel tabPanel;
  
  @Inject
  public AdminTabView() {
    widget = binder.createAndBindUi(this);
  }

  @Override
  public Tab addTab( String tabName, String historyToken, float priority ) {
    return tabPanel.addTab(tabName, historyToken, priority);
  }

  @Override
  public void removeTab(Tab tab) {
    tabPanel.removeTab(tab);
  }

  @Override
  public void removeTabs() {
    tabPanel.removeTabs();
  }

  @Override
  public void setActiveTab(Tab tab) {
    tabPanel.setActiveTab(tab);
  }

  @Override
  public void setTabContent(Widget widget) {
    tabPanel.setTabContent(widget);
  }


}
