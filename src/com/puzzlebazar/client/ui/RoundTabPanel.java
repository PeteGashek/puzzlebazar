package com.puzzlebazar.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.gwt.presenter.client.TabPanel;
import com.philbeaudoin.gwt.presenter.client.Tab;

/**
 * A widget that can show multiple tabs, each with its own content.
 * 
 * @author beaudoin
 */
public class RoundTabPanel extends Composite implements TabPanel {

  interface Binder extends UiBinder<Widget, RoundTabPanel> { }
  private static final Binder binder = GWT.create(Binder.class);

  private final List<Tab> tabList = new ArrayList<Tab>();

  @UiField
  HTMLPanel tabPanel;

  @UiField
  DivElement endTabMarker;

  @UiField
  FlowPanel tabContentContainer;

  Tab currentActiveTab = null;


  public RoundTabPanel() {
    initWidget( binder.createAndBindUi( this ) );
  }
  
  @Override
  public Tab addTab( String text, String historyToken, float priority ) {
    Tab newTab = new RoundTab( priority );
    newTab.setText( text );
    newTab.setHistoryToken( historyToken );
    Element addBeforeElement = null;
    for( Tab tab : tabList )
      if(newTab.getPriority() < tab.getPriority()) {
        addBeforeElement = tab.getElement();
        break;
      }
    if( addBeforeElement == null )
      addBeforeElement = endTabMarker;
    tabPanel.getElement().insertBefore( newTab.getElement(), addBeforeElement );
    tabList.add( newTab );
    return newTab;
  }

  @Override
  public void removeTab( Tab tab ) {
    tabPanel.getElement().removeChild( tab.getElement() );
    tabList.remove( tab );
  }

  @Override
  public void removeTabs() {
    for( Tab tab : tabList )
      tabPanel.getElement().removeChild( tab.getElement() );
    tabList.clear();
  } 
  
  @Override
  public void setActiveTab( Tab tab ) {
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

}
