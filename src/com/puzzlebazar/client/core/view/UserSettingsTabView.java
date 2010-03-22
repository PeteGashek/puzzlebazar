package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.Tab;
import com.philbeaudoin.platform.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.AdminTabPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsTabPresenter;
import com.puzzlebazar.client.ui.SimpleTabPanel;

public class UserSettingsTabView extends ViewImpl implements UserSettingsTabPresenter.MyView {

  interface Binder extends UiBinder<Widget, UserSettingsTabView> { }
  protected static final Binder binder = GWT.create(Binder.class);
  
  private final Widget widget;
  
  @UiField
  SimpleTabPanel tabPanel;
  
  @Inject
  public UserSettingsTabView() {
    widget = binder.createAndBindUi(this);
  }

  @Override 
  public Widget asWidget() {
    return widget;
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
  public void setContent(Object slot, Widget content) {
    if( slot == UserSettingsTabPresenter.TYPE_RevealTabContent )
      tabPanel.setPanelContent(content);
    else
      super.setContent(slot, content);
  }


}
