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
import com.puzzlebazar.client.presenter.SplitMainPresenter;

public class SplitMainProxy extends BasicProxy<SplitMainPresenter>  implements SplitMainPresenter.Proxy {

  public static final Type<SetContentHandler> TYPE_SetSideBarContent = new Type<SetContentHandler>();
  public static final Type<SetContentHandler> TYPE_SetCenterContent = new Type<SetContentHandler>();
  
  @Inject
  public SplitMainProxy(EventBus eventBus, Provider<SplitMainPresenter> presenter ) {
    super(eventBus, presenter);
  }

  @Override
  public void onBind() {
    super.onBind();
    registerHandler( eventBus.addHandler( TYPE_SetSideBarContent, new SetContentHandler(){
      @Override
      public void onSetContent(SetContentEvent setContentEvent) {
        getPresenter().setSideBarContent( setContentEvent.getContent() );
        getPresenter().revealDisplay();        
      }
    } ) );

    registerHandler( eventBus.addHandler( TYPE_SetCenterContent, new SetContentHandler(){
      @Override
      public void onSetContent(SetContentEvent setContentEvent) {
        getPresenter().setCenterContent( setContentEvent.getContent() );
        getPresenter().revealDisplay();        
      }
    } ) );
  }
  
}