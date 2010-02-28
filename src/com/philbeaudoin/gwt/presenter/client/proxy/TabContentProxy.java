package com.philbeaudoin.gwt.presenter.client.proxy;

import com.philbeaudoin.gwt.presenter.client.Tab;

public interface TabContentProxy extends ProxyPlace {

  /**
   * Retrieves the tab object associated with this presenter.
   * 
   * @return The tab object.
   */
  public Tab getTab();

  /**
   * Retrieves the text to show on that tab.
   * 
   * @return The text.
   */
  public String getText();


  /**
   * A tab priority indicates where it should appear within the tab 
   * strip. A tab with low priority will be placed more towards the 
   * left of the strip. Two tabs with the same priority will be placed
   * in an arbitrary order.
   * 
   * @return The priority.
   */
  public float getPriority();
  
}
