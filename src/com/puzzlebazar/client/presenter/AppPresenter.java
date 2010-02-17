package com.puzzlebazar.client.presenter;

import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;



public class AppPresenter extends BasicPresenter<AppPresenter.Display> {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
    void setTopBar( Widget topBar );
    void setLinkColumn( Widget linkColumn );
  }

  private final TopBarPresenter topBarPresenter;
  private final LinkColumnPresenter linkColumnPresenter;

  @Inject
  public AppPresenter(final Display display, final EventBus eventBus,       
      TopBarPresenter  topBarPresenter,
      LinkColumnPresenter linkColumnPresenter ) {
    super(display, eventBus);

    RootLayoutPanel.get().add(getWidget());
    
    this.topBarPresenter  = topBarPresenter;
    this.linkColumnPresenter = linkColumnPresenter;

    bind();
  }  

  @Override
  protected void onBind() {
    display.setTopBar( this.topBarPresenter.getWidget() );
    display.setLinkColumn( this.linkColumnPresenter.getWidget() );
    
  }

  @Override
  protected void onUnbind() {
    
  }

  @Override
  public void revealDisplay() {
    // TODO Auto-generated method stub    
  }


}
