package com.philbeaudoin.gwt.presenter.client;


public interface TabContainerPresenter extends Presenter {

  /**
   * Sets the content of this tab container.
   * 
   * @param content The {@link Presenter} to set as content.
   */
  public void setTabContent(Presenter content);

}
