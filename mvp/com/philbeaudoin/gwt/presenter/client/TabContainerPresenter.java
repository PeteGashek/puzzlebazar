package com.philbeaudoin.gwt.presenter.client;

import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxy;


public interface TabContainerPresenter extends Presenter {

  /**
   * Sets the content of this tab container.
   * 
   * @param content The {@link Presenter} to set as content.
   */
  public void setTabContent(Presenter content);

  /**
   * Adds a new tab to this presenter.
   * 
   * @param tabProxy The {@link TabContentProxy} containing information on the tab to add.
   * @return The newly added tab.
   */
  public Tab addTab(TabContentProxy tabProxy);

}
