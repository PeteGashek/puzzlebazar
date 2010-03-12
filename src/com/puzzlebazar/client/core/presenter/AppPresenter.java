package com.puzzlebazar.client.core.presenter;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.PresenterWidget;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;

public class AppPresenter extends PresenterImpl<AppPresenter.MyView, AppPresenter.MyProxy> {

  public interface MyView extends View {
    void setTopBar( Widget topBar );
    void setMainContent( Widget mainContent );
  }
  
  public interface MyProxy extends Proxy<AppPresenter> {}  

  private final TopBarPresenter topBarPresenter;

  private PresenterWidget mainContent = null;

  
  @Inject
  public AppPresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy,
      final TopBarPresenter topBarPresenter ) {
    super(eventBus, view, proxy);

    RootPanel.get().add(getWidget());
    
    this.topBarPresenter  = topBarPresenter;
  }  

  @Override
  protected void setContentInParent() {}
  
  @Override
  protected void onBind() {
    super.onBind();
    getView().setTopBar( this.topBarPresenter.getWidget() );
  }

  @Override
  public void onHide() {
    super.onHide();
    hideMainContent();    
  }
  
  public void setMainContent(PresenterWidget content) {
    if( mainContent != content ) {
      hideMainContent();
      mainContent = content;
      getView().setMainContent( content.getWidget() );
    }
  }
  
  private void hideMainContent() {
    if( mainContent != null )
      mainContent.onHide();
  }
}
