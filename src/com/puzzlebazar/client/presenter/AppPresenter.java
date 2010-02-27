package com.puzzlebazar.client.presenter;

import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.proxy.PresenterProxy;
import com.puzzlebazar.client.gin.annotations.DefaultMainPresenter;


public class AppPresenter extends BasicPresenter<AppPresenter.Display, AppPresenter.Proxy> {

  public interface Display extends PresenterDisplay {
    void setTopBar( Widget topBar );
    void setMainContent( Widget mainContent );
  }
  
  public interface Proxy extends PresenterProxy {}  

  private final TopBarPresenter topBarPresenter;
  private final Presenter defaultMainPresenter;

  private Presenter mainContent = null;

  
  @Inject
  public AppPresenter(final EventBus eventBus, final Display display, final Proxy proxy,
      final TopBarPresenter topBarPresenter,
      @DefaultMainPresenter final Presenter defaultMainPresenter ) {
    super(eventBus, display, proxy, null);

    RootLayoutPanel.get().add(getWidget());
    
    this.topBarPresenter  = topBarPresenter;
    this.defaultMainPresenter = defaultMainPresenter;
  }  

  @Override
  public void onBind() {
    super.onBind();
    display.setTopBar( this.topBarPresenter.getWidget() );
    setMainContent( defaultMainPresenter );
  }

  public void setMainContent(Presenter content) {
    if( mainContent != content ) {
      mainContent = content;
      getDisplay().setMainContent( content.getWidget() );      
    }
  }
  
  
}
