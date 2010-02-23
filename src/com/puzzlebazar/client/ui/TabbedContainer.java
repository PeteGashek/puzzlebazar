package com.puzzlebazar.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A container that can show multiple tabs, each with its own content.
 * 
 * @author beaudoin
 */
public class TabbedContainer extends Composite implements HasTabs {

  interface Binder extends UiBinder<Widget, TabbedContainer> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField
  HTMLPanel tabPanel;

  @UiField
  DivElement endTabMarker;

  Tab currentActiveTab = null;

  public TabbedContainer() {
    initWidget( binder.createAndBindUi( this ) );
  }
  
  @Override
  public Tab addTab( String text, String historyToken ) {
    Tab tab = new Tab();
    tab.setText( text );
    tab.setHistoryToken( historyToken );
    tabPanel.getElement().insertBefore( tab.getElement(), endTabMarker );
    return tab;
  }

  @Override
  public void removeTab( Tab tab ) {
    tabPanel.getElement().removeChild( tab.getElement() );
  }
  
  @Override
  public void setActiveTab( Tab tab ) {
    if( currentActiveTab != null )
      currentActiveTab.deactivate();
    tab.activate();
    currentActiveTab = tab;
  } 

}
