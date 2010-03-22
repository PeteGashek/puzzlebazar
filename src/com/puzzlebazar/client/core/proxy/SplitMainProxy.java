/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.StandardProvider;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyFailureHandler;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentHandler;
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;

public class SplitMainProxy extends ProxyImpl<SplitMainPresenter>  
implements SplitMainPresenter.MyProxy {

  private RevealContentHandler<SplitMainPresenter> revealContentHandler = null;  

  @Inject
  public SplitMainProxy(Provider<SplitMainPresenter> presenter ) {
    super.presenter = new StandardProvider<SplitMainPresenter>(presenter);
  }

  @Inject
  protected void bind(ProxyFailureHandler failureHandler, EventBus eventBus) {
    revealContentHandler = new RevealContentHandler<SplitMainPresenter>( failureHandler, this );    
    eventBus.addHandler( SplitMainPresenter.TYPE_RevealSideBarContent, revealContentHandler );
    eventBus.addHandler( SplitMainPresenter.TYPE_RevealCenterContent,  revealContentHandler );
  }

}