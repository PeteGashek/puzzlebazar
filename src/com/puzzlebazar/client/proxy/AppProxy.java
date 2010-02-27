/**
 * 
 */
package com.puzzlebazar.client.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.BasicProxy;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentHandler;
import com.puzzlebazar.client.presenter.AppPresenter;

public class AppProxy extends BasicProxy<AppPresenter> implements AppPresenter.Proxy {

  public static final Type<SetContentHandler> TYPE_SetMainContent = new Type<SetContentHandler>();

  @Inject
  public AppProxy(EventBus eventBus, Provider<AppPresenter> presenter ) {
    super(eventBus, presenter);
  }
  
  @Override
  public void onBind() {
    super.onBind();
    registerHandler( eventBus.addHandler( TYPE_SetMainContent, new SetContentHandler(){
      @Override
      public void onSetContent(SetContentEvent setContentEvent) {
        getPresenter().setMainContent( setContentEvent.getContent() );
        getPresenter().revealDisplay();
      }
    } ) );
  }
}