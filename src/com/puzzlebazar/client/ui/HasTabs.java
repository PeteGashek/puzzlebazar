package com.puzzlebazar.client.ui;

import com.google.gwt.user.client.ui.Widget;

public interface HasTabs {

  /**
   * Adds a new tab to the widget.
   * 
   * @param text The text to display on the tab.
   * @param historyToken The history token the tab points to.
   * @return The newly added tab.
   */
  public Tab addTab( String text, String historyToken );
  
  
  /**
   * Removes a tab from the widget.
   * 
   * @param tab The tab to remove
   */
  public void removeTab( Tab tab );

  /**
   * Sets the currently active tab.
   * 
   * @param tab The tab to activate
   */
  public void setActiveTab( Tab tab );


  /**
   * Changes the currently displayed content
   * 
   * @param widget The content to display
   */
  public void setTabContent( Widget widget );
  
}
