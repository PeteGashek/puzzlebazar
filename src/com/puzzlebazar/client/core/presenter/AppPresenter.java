package com.puzzlebazar.client.core.presenter;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.View;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterWidget;
import com.philbeaudoin.gwt.presenter.client.ViewInterface;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyInterface;

public class AppPresenter extends PresenterImpl<AppPresenter.MyView, AppPresenter.MyProxy> {

  @ViewInterface
  public interface MyView extends View {
    void setTopBar( Widget topBar );
    void setMainContent( Widget mainContent );
  }
  
  @ProxyInterface
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
