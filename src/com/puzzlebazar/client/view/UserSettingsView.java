package com.puzzlebazar.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.presenter.UserSettingsPresenter;
import com.puzzlebazar.client.ui.Tab;
import com.puzzlebazar.client.ui.TabbedContainer;

public class UserSettingsView implements UserSettingsPresenter.Display {
  
  interface Binder extends UiBinder<Widget, UserSettingsView> { }
  private static final Binder binder = GWT.create(Binder.class);

  private final Widget widget;
  
  @UiField
  TabbedContainer tabbedContainer;
  
  @Inject
  public UserSettingsView() {
    widget =  binder.createAndBindUi(this);
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  @Override
  public Tab addTab( String tabName, String tabLink ) {
    return tabbedContainer.addTab(tabName, tabLink);
  }

  @Override
  public void removeTab(Tab tab) {
    tabbedContainer.removeTab(tab);
  }

  @Override
  public void setActiveTab(Tab tab) {
    tabbedContainer.setActiveTab(tab);
  }

  @Override
  public void setTabContent(Widget widget) {
    tabbedContainer.setTabContent(widget);
  }


}
