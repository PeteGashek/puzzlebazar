/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.DirectProvider;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.SetContentEvent;
import com.philbeaudoin.platform.mvp.client.proxy.SetContentHandler;
import com.puzzlebazar.client.core.presenter.AppPresenter;

public class AppProxy extends ProxyImpl<AppPresenter> implements AppPresenter.MyProxy {

  public static final Type<SetContentHandler<?>> TYPE_SetMainContent = new Type<SetContentHandler<?>>();

  @Inject
  public AppProxy(EventBus eventBus, Provider<AppPresenter> presenter ) {
    super(eventBus, new DirectProvider<AppPresenter>(presenter) );
  }
  
  @Override
  protected void onBind() {
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