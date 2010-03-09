package com.puzzlebazar.client.core.presenter;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterWidget;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;

public class AppPresenter extends PresenterImpl<AppPresenter.MyDisplay, AppPresenter.MyProxy> {

  public interface MyDisplay extends Display {
    void setTopBar( Widget topBar );
    void setMainContent( Widget mainContent );
  }
  
  public interface MyProxy extends Proxy<AppPresenter> {}  

  private final TopBarPresenter topBarPresenter;

  private PresenterWidget mainContent = null;

  
  @Inject
  public AppPresenter(
      final EventBus eventBus, 
      final MyDisplay display, 
      final MyProxy proxy,
      final TopBarPresenter topBarPresenter ) {
    super(eventBus, display, proxy);

    RootPanel.get().add(getWidget());
    
    this.topBarPresenter  = topBarPresenter;
  }  

  @Override
  protected void setContentInParent() {}
  
  @Override
  protected void onBind() {
    super.onBind();
    getDisplay().setTopBar( this.topBarPresenter.getWidget() );
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
      getDisplay().setMainContent( content.getWidget() );
    }
  }
  
  private void hideMainContent() {
    if( mainContent != null )
      mainContent.onHide();
  }
}
