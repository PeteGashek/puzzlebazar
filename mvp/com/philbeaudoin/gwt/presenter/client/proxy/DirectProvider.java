package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.inject.Provider;

/**
 * A {@link CallbackProvider} that directly gets the object and 
 * invokes the callback. This is essentially the same as a
 * standard {@link Provider}, but shares the interface of other
 * {@link CallbackProvider}.
 *
 * @param <T> The type of the provided object.
 * 
 * @author Philippe Beaudoin
 */
public class DirectProvider<T> implements CallbackProvider<T> {

  private final Provider<T> provider;

  /**
   * Creates a {@link CallbackProvider} that directly gets the object and 
   * invokes the callback.
   * 
   * @param provider The {@link Provider} of the object. 
   */
  public DirectProvider( Provider<T> provider ) {
    this.provider = provider;
  }

  @Override
  public void get(Callback<T> callback) {
    callback.execute( provider.get() );
  }

}
