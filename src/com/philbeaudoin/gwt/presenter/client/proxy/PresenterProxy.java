package com.philbeaudoin.gwt.presenter.client.proxy;

import com.philbeaudoin.gwt.presenter.client.Presenter;

/**
 * The interface for light-weight singleton classes 
 * that listens for events before the full {@link Presenter} 
 * is instantiated. This include, among others, the presenter's 
 * specific {@link SetContentEvent} that needs the presenter to 
 * reveal itself.
 * <p />
 * The relationship between a presenter and its proxy is two-way.
 * <p />
 * This class is called PresenterProxy instead of simply Proxy
 * because {@link Presenter} subclasses will usually define 
 * their own interface called Proxy and derived from this one. 
 * Naming this interface Proxy would therefore be impractical
 * for code-writing purposes.
 * 
 * @author beaudoin
 */
public interface PresenterProxy {

  /**
   * Access the presenter associated with this proxy. This method should be invoked only
   * when the presenter is or needs to be revealed, as it will cause the presenter and
   * its view to be instantiated. 
   * 
   * @return The associated presenter.
   */
  public Presenter getPresenter();

  /**
   * Called by this proxy's presenter whenever it has changed in a way that would require 
   * the parameters in the HistoryToken bar to be changed. If you override, make sure you call
   * your parent onPresenterChanged().
   */
  public void onPresenterChanged();

  /**
   * Called by this proxy's presenter whenever it has been revealed. If you override, make sure you call
   * your parent onPresenterRevealed().
   */
  public void onPresenterRevealed();
  
}