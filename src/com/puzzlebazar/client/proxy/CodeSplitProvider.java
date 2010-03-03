package com.puzzlebazar.client.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.philbeaudoin.gwt.presenter.client.proxy.Callback;
import com.philbeaudoin.gwt.presenter.client.proxy.CallbackProvider;
import com.puzzlebazar.client.resources.Translations;

public class CodeSplitProvider<T> implements CallbackProvider<T> {

  private final Translations translations;
  
  private final AsyncProvider<T> provider;
  
  public CodeSplitProvider( AsyncProvider<T> provider, Translations translations ) {
    this.provider = provider;
    this.translations = translations;
  }
  
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