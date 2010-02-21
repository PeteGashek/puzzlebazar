package com.puzzlebazar.client.presenter;

import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.puzzlebazar.client.gin.annotations.DefaultMainPresenter;
import com.puzzlebazar.client.presenter.event.NewMainContentEvent;
import com.puzzlebazar.client.presenter.event.NewMainContentHandler;



public class AppPresenter extends BasicPresenter<AppPresenter.Display> implements NewMainContentHandler {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
    void setTopBar( Widget topBar );
    void setMainContent( Widget mainContent );
  }

  private final TopBarPresenter topBarPresenter;
  private Presenter mainPresenter = null;

  @Inject
  public AppPresenter(final Display display, final EventBus eventBus,       
      TopBarPresenter  topBarPresenter,
      @DefaultMainPresenter Presenter defaultMainPresenter ) {
    super(display, eventBus);

    RootLayoutPanel.get().add(getWidget());
    
    this.topBarPresenter  = topBarPresenter;
    this.mainPresenter    = defaultMainPresenter;

    bind();
  }  

  @Override
  protected void onBind() {
    display.setTopBar( this.topBarPresenter.getWidget() );
    
    registerHandler( eventBus.addHandler( NewMainContentEvent.getType(), this ) ); 

    displayMainContent();
  }

  @Override
  protected void onUnbind() {
    
  }

  @Override
  public void revealDisplay() {
  }

  @Override
  public void onNewMainContent(NewMainContentEvent event) {
    if( mainPresenter != event.getPresenter() ) {
    mainPresenter = event.getPresenter();
    displayMainContent();
    }
  }
  
  /**
   * Make sure the main content is the correct one
   */
  private void displayMainContent() {
    display.setMainContent( mainPresenter.getWidget() );
    revealDisplay();
  }


}
