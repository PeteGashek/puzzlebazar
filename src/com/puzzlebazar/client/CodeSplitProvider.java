package com.puzzlebazar.client;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.philbeaudoin.platform.mvp.client.proxy.Callback;
import com.philbeaudoin.platform.mvp.client.proxy.CallbackProvider;
import com.puzzlebazar.client.resources.Translations;


/**
 * A {@link CallbackProvider} that gets the object using
 * code splitting and invokes the callback once the code
 * is loaded. This is essentially the same as a standard 
 * {@link AsyncProvider}, but shares the interface of other
 * {@link CallbackProvider}.
 *
 * @param <T> The type of the provided object.
 * 
 * @author Philippe Beaudoin
 */
public class CodeSplitProvider<T> implements CallbackProvider<T> {

  private final Translations translations;

  private final AsyncProvider<T> provider;

  /**
   * Construct {@link CallbackProvider} that gets the object using
   * code splitting and invokes the callback once the code
   * is loaded.
   * 
   * @param provider The {@link AsyncProvider} providing the object.
   * @param translations The {@link Translations}.
   */
  public CodeSplitProvider( AsyncProvider<T> provider, Translations translations ) {
    this.provider = provider;
    this.translations = translations;
  }

  @Override
  public void get(final Callback<T> callback) {
    provider.get( new AsyncCallback<T>(){
      @Override
      public void onFailure(Throwable caught) {
        Window.alert( translations.codeLoadFailure() );
      }

      @Override
      public void onSuccess(T parameter) {
        callback.execute(parameter);
      }} );
  }

}