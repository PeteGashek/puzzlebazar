package com.puzzlebazar.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.platform.mvp.client.Tab;
import com.philbeaudoin.platform.mvp.client.TabPanel;

public abstract class BaseTabPanel extends Composite implements TabPanel {

  @UiField
  HTMLPanel tabPanel;

  @UiField
  DivElement endTabMarker;

  @UiField
  FlowPanel tabContentContainer;

  private final List<Tab> tabList = new ArrayList<Tab>();
  Tab currentActiveTab = null;
  
  public BaseTabPanel() {
    super();
  }

  @Override
  public Tab addTab(String text, String historyToken, float priority) {
    Tab newTab = CreateNewTab( priority );
    newTab.setText( text );
    newTab.setTargetHistoryToken( historyToken );
    Element addBeforeElement = null;
    for( Tab tab : tabList )
      if(newTab.getPriority() < tab.getPriority()) {
        addBeforeElement = tab.asWidget().getElement();
        break;
      }
    if( addBeforeElement == null )
      addBeforeElement = endTabMarker;
    tabPanel.getElement().insertBefore( newTab.asWidget().getElement(), addBeforeElement );
    tabList.add( newTab );
    return newTab;
  }


  @Override
  public void removeTab(Tab tab) {
    tabPanel.getElement().removeChild( tab.asWidget().getElement() );
    tabList.remove( tab );
  }

  @Override
  public void removeTabs() {
    for( Tab tab : tabList )
      tabPanel.getElement().removeChild( tab.asWidget().getElement() );
    tabList.clear();
  }


  @Override
  public void setActiveTab(Tab tab) {
    if( currentActiveTab != null )
      currentActiveTab.deactivate();
    tab.activate();
    currentActiveTab = tab;
  }

  @Override
  public void setTabContent(Widget tabContent) {
    tabContentContainer.clear();
    tabContentContainer.add( tabContent );
  }

  /**
   * Returns a new tab of the type specific for this tab panel.
   * 
   * @param priority The desired priority of the new tab.
   * @return The new tab.
   */
  protected abstract Tab CreateNewTab(float priority);  

}