package com.puzzlebazar.client.core.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterWidget;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.puzzlebazar.client.core.proxy.AppProxy;


public class SplitMainPresenter extends PresenterImpl<SplitMainPresenter.MyDisplay, SplitMainPresenter.MyProxy> {


  public interface MyDisplay extends Display {
    void setSideBarContent( Widget sideBarContent );
    void setCenterContent( Widget centerContent );
  }

  public interface MyProxy extends Proxy<SplitMainPresenter> {}

  private final LinkColumnPresenter linkColumnPresenter;
  private PresenterWidget sideBarContent = null;
  private PresenterWidget centerContent = null;

  @Inject
  public SplitMainPresenter(
      final EventBus eventBus, 
      final MyDisplay display, 
      final MyProxy proxy,
      final LinkColumnPresenter  linkColumnPresenter ) {
    super(eventBus, display, proxy);

    this.linkColumnPresenter = linkColumnPresenter;
  }  

  @Override
  protected void setContentInParent() {
    SetContentEvent.fire(eventBus, AppProxy.TYPE_SetMainContent, this);
  }
  
  @Override
  protected void onBind() {
    super.onBind();
    setSideBarContent( linkColumnPresenter );
  }

  @Override
  public void onHide() {
    super.onHide();
    hideSideBarContent();    
    hideCenterContent();    
  }


  public void setSideBarContent(PresenterWidget content) {
    if( sideBarContent != content ) {
      hideSideBarContent();
      sideBarContent = content;
      getDisplay().setSideBarContent( content.getWidget() );
    }
  }
  
  public void setCenterContent(PresenterWidget content) {
    if( centerContent  != content ) {
      hideCenterContent();
      centerContent = content;
      getDisplay().setCenterContent( content.getWidget() );
    }
  } 

  private void hideSideBarContent() {
    if( sideBarContent != null )
      sideBarContent.onHide();
  }  


  private void hideCenterContent() {
    if( centerContent != null )
      centerContent.onHide();
  }
}
