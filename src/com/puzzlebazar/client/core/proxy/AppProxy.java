/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.DirectProvider;
import com.philbeaudoin.gwt.presenter.client.proxy.PresenterProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentHandler;
import com.puzzlebazar.client.core.presenter.AppPresenter;

public class AppProxy extends PresenterProxyImpl<AppPresenter> implements AppPresenter.Proxy {

  public static final Type<SetContentHandler<?>> TYPE_SetMainContent = new Type<SetContentHandler<?>>();

  @Inject
  public AppProxy(EventBus eventBus, Provider<AppPresenter> presenter ) {
    super(eventBus, new DirectProvider<AppPresenter>(presenter) );
  }
  
  @Override
  public void onBind() {
    super.onBind();
    registerHandler( eventBus.addHandler( TYPE_SetMainContent, new SetContentHandler<AppPresenter>(this){
      @Override
      public void onSetContent(
          AppPresenter presenter,
          SetContentEvent setContentEvent) {
            presenter.setMainContent( setContentEvent.getContent() );
            presenter.reveal();
      }
    } ) );
  }
}