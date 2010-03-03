package com.puzzlebazar.client.presenter;

import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.proxy.PresenterProxy;


public class AppPresenter extends PresenterImpl<AppPresenter.Display, AppPresenter.Proxy> {

  public interface Display extends PresenterDisplay {
    void setTopBar( Widget topBar );
    void setMainContent( Widget mainContent );
  }
  
  public interface Proxy extends PresenterProxy {}  

  private final TopBarPresenter topBarPresenter;

  private Presenter mainContent = null;

  
  @Inject
  public AppPresenter(
      final EventBus eventBus, 
      final Provider<Display> display, 
      final Proxy proxy,
      final TopBarPresenter topBarPresenter ) {
    super(eventBus, display, proxy, null);

    RootLayoutPanel.get().add(getWidget());
    
    this.topBarPresenter  = topBarPresenter;
  }  

  @Override
  public void onBind() {
    super.onBind();
    getDisplay().setTopBar( this.topBarPresenter.getWidget() );
  }

  public void setMainContent(Presenter content) {
    if( mainContent != content ) {
      mainContent = content;
      getDisplay().setMainContent( content.getWidget() );      
    }
  }
  
  
}
