package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.inject.Provider;

public class DirectProvider<T> implements CallbackProvider<T> {

  private final Provider<T> provider;
  
  public DirectProvider( Provider<T> provider ) {
    this.provider = provider;
  }
  
  public void get(Callback<T> callback) {
    callback.execute( provider.get() );
  }

}
