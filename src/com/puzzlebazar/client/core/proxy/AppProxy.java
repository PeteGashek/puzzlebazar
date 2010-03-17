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
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentHandler;
import com.puzzlebazar.client.core.presenter.AppPresenter;

public class AppProxy extends ProxyImpl<AppPresenter> implements AppPresenter.MyProxy {

  public static final Type<RevealContentHandler<?>> TYPE_RevealTopBarContent = new Type<RevealContentHandler<?>>();
  public static final Type<RevealContentHandler<?>> TYPE_RevealMainContent = new Type<RevealContentHandler<?>>();

  @Inject
  public AppProxy(EventBus eventBus, Provider<AppPresenter> presenter ) {
    super(eventBus, new DirectProvider<AppPresenter>(presenter) );
  }

  @Override
  protected void onBind() {
    super.onBind();

    registerHandler( eventBus.addHandler( TYPE_RevealTopBarContent, new RevealContentHandler<AppPresenter>(this){
      @Override
      public void onRevealContent(
          AppPresenter presenter,
          RevealContentEvent revealContentEvent) {
        presenter.setTopBarContent( revealContentEvent.getContent() );
        presenter.reveal();
      }
    } ) );

    registerHandler( eventBus.addHandler( TYPE_RevealMainContent, new RevealContentHandler<AppPresenter>(this){
      @Override
      public void onRevealContent(
          AppPresenter presenter,
          RevealContentEvent revealContentEvent) {
        presenter.setMainContent( revealContentEvent.getContent() );
        presenter.reveal();
      }
    } ) );

  }
}