package com.puzzlebazar.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

/**
 * Semi-opaque class that is managed by TabbedContainer. For that purpose,
 * most methods are package-private.
 * 
 * @author beaudoin
 */
public class Tab extends Composite {

  interface Binder extends UiBinder<Widget, Tab> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField
  Hyperlink hyperlink;

  /**
   * 
   */
  Tab() {
    initWidget( binder.createAndBindUi( this ) );
  }
  
  public void setTabName(String tabName) {
    hyperlink.setText(tabName);
  }
  
  public void setHistoryToken(String historyToken) {
    hyperlink.setTargetHistoryToken(historyToken);      
  }

  /**
   * Should not be called directly. Call
   * {@link TabbedContainer#setActiveTab(Tab)} instead.
   */
  void activate() {
    removeStyleName( "inactive" );
    addStyleName( "active" );
  }

  /**
   * Should not be called directly. Call
   * {@link TabbedContainer#setActiveTab(Tab)} instead.
   */
  void deactivate() {
    removeStyleName( "active" );
    addStyleName( "inactive" );
  }
  
  
  
}
