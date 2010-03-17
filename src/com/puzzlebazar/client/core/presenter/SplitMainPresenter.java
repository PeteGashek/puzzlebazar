package com.puzzlebazar.client.core.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.Presenter;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentEvent;
import com.puzzlebazar.client.core.proxy.AppProxy;

public class SplitMainPresenter 
extends PresenterImpl<SplitMainPresenter.MyView, SplitMainPresenter.MyProxy>
implements DisplayShortMessageHandler {

  public interface MyView extends View {
    public void showMessage( String message, boolean dismissable );
    public void clearMessage();
    public void setSideBarContent( Widget sideBarContent );
    public void setCenterContent( Widget centerContent );
  }

  public interface MyProxy extends Proxy<SplitMainPresenter> {}

  private Presenter sideBarContent = null;
  private Presenter centerContent = null;

  @Inject
  public SplitMainPresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy ) {
    super(eventBus, view, proxy);
  }  

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(eventBus, AppProxy.TYPE_RevealMainContent, this);
  }

  @Override
  protected void onBind() {
    super.onBind();
    registerHandler( eventBus.addHandler( DisplayShortMessageEvent.getType(), this ) );
  }

  @Override
  protected void revealChildren() {
    super.revealChildren();
    if( sideBarContent != null )
      sideBarContent.reveal();
    if( centerContent != null )
      centerContent.reveal();
  }
  
  @Override
  protected void notifyHideChildren() {
    super.notifyHideChildren();
    if( sideBarContent != null )
      sideBarContent.notifyHide();
    if( centerContent != null )
      centerContent.notifyHide();
  }  
  
  public void setSideBarContent(Presenter content) {
    if( sideBarContent != content ) {
      if( sideBarContent != null )
        sideBarContent.notifyHide();
      sideBarContent = content;
      getView().setSideBarContent( content.getWidget() );
    }
  }

  public void setCenterContent(Presenter content) {
    if( centerContent  != content ) {
      if( centerContent != null )
        centerContent.notifyHide();
      centerContent = content;
      getView().setCenterContent( content.getWidget() );
    }
  } 

  @Override
  public void onDisplayShortMessage(DisplayShortMessageEvent event) {
    if( !isVisible() || event.isAlreadyDisplayed() )
      return;
    String message = event.getMessage();    
    if( message == null )
      view.clearMessage();
    else    
      // TODO Take duration into account
      view.showMessage( event.getMessage(), event.isDismissable() );
    event.setAlreadyDisplayed();
  }
}
