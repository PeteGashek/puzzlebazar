package com.puzzlebazar.client.core.presenter;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.Presenter;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;

public class AppPresenter extends PresenterImpl<AppPresenter.MyView, AppPresenter.MyProxy> {

  public interface MyView extends View {
    void setTopBarContent( Widget topBarContent );
    void setMainContent( Widget mainContent );
  }
  
  public interface MyProxy extends Proxy<AppPresenter> {}  

  private Presenter topBarContent = null;
  private Presenter mainContent = null;
  
  @Inject
  public AppPresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy ) {
    super(eventBus, view, proxy);
  }  

  @Override
  protected void revealInParent() {
    RootPanel.get().add(getWidget());    
  }
  
  @Override
  protected void revealChildren() {
    super.revealChildren();
    if( topBarContent != null )
      topBarContent.reveal();
    if( mainContent != null )
      mainContent.reveal();
  }
  
  @Override
  protected void notifyHideChildren() {
    super.notifyHideChildren();
    if( topBarContent != null )
      topBarContent.notifyHide();
    if( mainContent != null )
      mainContent.notifyHide();
  }  
    
  public void setTopBarContent(Presenter content) {
    if( topBarContent != content ) {
      if( topBarContent != null )
        topBarContent.notifyHide();
      topBarContent = content;
      getView().setTopBarContent( content.getWidget() );
    }
  }
  
  public void setMainContent(Presenter content) {
    if( mainContent != content ) {
      if( mainContent != null )
        mainContent.notifyHide();
      mainContent = content;
      getView().setMainContent( content.getWidget() );
    }
  }

}
