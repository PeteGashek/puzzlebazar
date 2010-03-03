package com.puzzlebazar.client;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.proxy.Callback;
import com.philbeaudoin.gwt.presenter.client.proxy.CallbackProvider;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.resources.Translations;

public class Bundle {

  protected final Provider<?> providers[] ;

  public Bundle( int bundleSize ) {
    providers = new Provider<?>[bundleSize];
  }

  /**
   * Accesses a provider given its id.
   * 
   * @param providerId The id of the provider to access.
   * @return The provider.
   */
  public Provider<?> get(int providerId) {
    return providers[providerId];
  }
  
  public static class CodeSplitProvider<T> implements CallbackProvider<T> {
  
    private final Translations translations;
    
    private final AsyncProvider<TabbedPresenterBundle> bundleProvider;
    private final int providerId;
    
    public CodeSplitProvider( AsyncProvider<TabbedPresenterBundle> bundleProvider, int providerId, Translations translations ) {
      this.bundleProvider = bundleProvider;
      this.providerId = providerId;
      this.translations = translations;
    }
    
    public void get(final Callback<T> callback) {
      bundleProvider.get( new AsyncCallback<TabbedPresenterBundle>(){
        @Override
        public void onFailure(Throwable caught) {
          Window.alert( translations.codeLoadFailure() );
        }
  
        @SuppressWarnings("unchecked")
        @Override
        public void onSuccess(TabbedPresenterBundle parameter) {
          callback.execute(((Provider<T>)parameter.get(providerId)).get());
        }} );
    }
  
  }

}