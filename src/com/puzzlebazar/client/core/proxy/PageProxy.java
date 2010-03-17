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
import com.puzzlebazar.client.core.presenter.PagePresenter;

/**
 * The {@link Proxy} for {@link PagePresenter}.
 * 
 * @author Philippe Beaudoin
 */
public class PageProxy extends ProxyImpl<PagePresenter> implements PagePresenter.MyProxy {

  public static final Type<RevealContentHandler<?>> TYPE_RevealMainContent = new Type<RevealContentHandler<?>>();

  @Inject
  public PageProxy(EventBus eventBus, Provider<PagePresenter> presenter ) {
    super(eventBus, new DirectProvider<PagePresenter>(presenter) );
  }

  @Override
  protected void onBind() {
    super.onBind();

    registerHandler( eventBus.addHandler( TYPE_RevealMainContent, new RevealContentHandler<PagePresenter>(this){
      @Override
      public void onRevealContent(
          PagePresenter presenter,
          RevealContentEvent revealContentEvent) {
        presenter.setMainContent( revealContentEvent.getContent() );
        presenter.reveal();
      }
    } ) );

  }
}