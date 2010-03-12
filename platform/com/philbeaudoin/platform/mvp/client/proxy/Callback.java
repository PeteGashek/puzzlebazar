package com.philbeaudoin.platform.mvp.client.proxy;

/**
 * A simple callback interface.
 *
 * @param <T> The parameter of the callback.
 * 
 * @author beaudoin
 */
public interface Callback<T> {
  /**
   * Called when the callback is executed.
   * 
   * @param parameter The parameter passed to the callback
   */
  void execute(T parameter);
}
