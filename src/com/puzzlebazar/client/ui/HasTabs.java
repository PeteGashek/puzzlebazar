package com.puzzlebazar.client.ui;

public interface HasTabs {

  /**
   * Adds a new tab to the widget
   * 
   * @param tabName The name of the newly added tab
   * @param historyToken The history token the tab points to
   * @return The newly added tab (a semi-opaque object)
   */
  public Tab addTab( String tabName, String historyToken );
  
  
  /**
   * Removes a tab from the widget
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
}
