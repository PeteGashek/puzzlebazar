/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Tab;
import com.gwtplatform.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.AdminTabPresenter;
import com.puzzlebazar.client.ui.SimpleTabPanel;

public class AdminTabView extends ViewImpl implements AdminTabPresenter.MyView {
  
  interface Binder extends UiBinder<Widget, AdminTabView> { }
  protected static final Binder binder = GWT.create(Binder.class);

  private final Widget widget;

  @UiField
  SimpleTabPanel tabPanel;
  
  @Inject
  public AdminTabView() {
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
    if( slot == AdminTabPresenter.TYPE_RevealTabContent )
      tabPanel.setPanelContent(content);
    else
      super.setContent(slot, content);
  }


}
