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
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;

public class SplitMainProxy extends PresenterProxyImpl<SplitMainPresenter>  implements SplitMainPresenter.Proxy {

  public static final Type<SetContentHandler<?>> TYPE_SetSideBarContent = new Type<SetContentHandler<?>>();
  public static final Type<SetContentHandler<?>> TYPE_SetCenterContent = new Type<SetContentHandler<?>>();

  @Inject
  public SplitMainProxy(EventBus eventBus, Provider<SplitMainPresenter> presenter ) {
    super(eventBus, new DirectProvider<SplitMainPresenter>(presenter) );
  }

  @Override
  public void onBind() {
    super.onBind();
    
    registerHandler( eventBus.addHandler( TYPE_SetSideBarContent, 
        new SetContentHandler<SplitMainPresenter>(this){
      @Override
      public void onSetContent(SplitMainPresenter presenter,
          SetContentEvent setContentEvent) {
        presenter.setSideBarContent( setContentEvent.getContent() );
        presenter.reveal();
      }
    } ) );

    registerHandler( eventBus.addHandler( TYPE_SetCenterContent, 
        new SetContentHandler<SplitMainPresenter>(this){
      @Override
      public void onSetContent(SplitMainPresenter presenter,
          SetContentEvent setContentEvent) {
        presenter.setCenterContent( setContentEvent.getContent() );
        presenter.reveal();
      }
    } ) );
    
  }

}