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
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;

public class SplitMainProxy extends ProxyImpl<SplitMainPresenter>  implements SplitMainPresenter.MyProxy {

  public static final Type<RevealContentHandler<?>> TYPE_RevealSideBarContent = new Type<RevealContentHandler<?>>();
  public static final Type<RevealContentHandler<?>> TYPE_RevealCenterContent = new Type<RevealContentHandler<?>>();

  @Inject
  public SplitMainProxy(EventBus eventBus, Provider<SplitMainPresenter> presenter ) {
    super(eventBus, new DirectProvider<SplitMainPresenter>(presenter) );
  }

  @Override
  protected void onBind() {
    super.onBind();

    registerHandler( eventBus.addHandler( TYPE_RevealSideBarContent, 
        new RevealContentHandler<SplitMainPresenter>(this){
      @Override
      public void onRevealContent(
          final SplitMainPresenter presenter,
          final RevealContentEvent revealContentEvent) {
        presenter.setSideBarContent( revealContentEvent.getContent() );
        presenter.reveal();
      }
    } ) );

    registerHandler( eventBus.addHandler( TYPE_RevealCenterContent, 
        new RevealContentHandler<SplitMainPresenter>(this){
      @Override
      public void onRevealContent(
          final SplitMainPresenter presenter,
          final RevealContentEvent revealContentEvent) {
        presenter.setCenterContent( revealContentEvent.getContent() );
        presenter.reveal();
      }
    } ) );
    
  }

}