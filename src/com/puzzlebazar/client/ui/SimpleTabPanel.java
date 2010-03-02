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
 * A widget that can show multiple tabs with rounded corners, each with its own content.
 * 
 * @author Philippe Beaudoin
 */
public class SimpleTabPanel extends Composite implements TabPanel {

  interface Binder extends UiBinder<Widget, SimpleTabPanel> { }
  private static final Binder binder = GWT.create(Binder.class);

  private final List<Tab> tabList = new ArrayList<Tab>();

  @UiField
  HTMLPanel tabPanel;

  @UiField
  DivElement endTabMarker;

  @UiField
  FlowPanel tabContentContainer;

  Tab currentActiveTab = null;


  public SimpleTabPanel() {
    initWidget( binder.createAndBindUi( this ) );
  }
  
  @Override
  public Tab addTab( String text, String historyToken, float priority ) {
    SimpleTab newTab = new SimpleTab( priority );
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
    tabPanel.getElement().insertBefore( newTab.getElement(), addBeforeElement );
    tabList.add( newTab );
    return newTab;
  }

  @Override
  public void removeTab( Tab tab ) {
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
