/**
 * 
 */
package com.philbeaudoin.platform.mvp.client.proxy;

public interface ProxyFailureHandler {
  void onFailedGetPresenter(Throwable caught);
}