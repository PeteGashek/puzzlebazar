package com.philbeaudoin.gwt.presenter.client.proxy;

import com.puzzlebazar.client.ui.Tab;

public interface TabContentProxy extends ProxyPlace {

  /**
   * Sets the tab object associated with this presenter.
   * 
   * @param tab The tab object.
   */
  public void setTab(Tab tab);

  /**
   * Retrieves the tab object associated with this presenter.
   * 
   * @return The tab object.
   */
  public Tab getTab();
  
}
