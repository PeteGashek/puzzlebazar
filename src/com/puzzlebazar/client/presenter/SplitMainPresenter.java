package com.puzzlebazar.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.puzzlebazar.client.presenter.event.NewCenterContentEvent;
import com.puzzlebazar.client.presenter.event.NewCenterContentHandler;
import com.puzzlebazar.client.presenter.event.NewMainContentEvent;



public class SplitMainPresenter extends BasicPresenter<SplitMainPresenter.Display> implements NewCenterContentHandler {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
    void setSideBar( Widget sideBar );
    void setCenterContent( Widget centerContent );
  }

  private final LinkColumnPresenter linkColumnPresenter;
  private Presenter centerPresenter = null;

  @Inject
  public SplitMainPresenter(final Display display, final EventBus eventBus,       
      LinkColumnPresenter  linkColumnPresenter ) {
    super(display, eventBus);

    this.linkColumnPresenter = linkColumnPresenter;

    bind();
  }  

  @Override
  protected void onBind() {
    registerHandler( eventBus.addHandler( NewCenterContentEvent.getType(), this ) ); 

    display.setSideBar( this.linkColumnPresenter.getWidget() );
  }

  @Override
  protected void onUnbind() {

  }

  @Override
  public void revealDisplay() {
    NewMainContentEvent.fire(eventBus, this);    
  }

  @Override
  public void onNewCenterContent(NewCenterContentEvent event) {
    if( centerPresenter  != event.getPresenter() ) {
      centerPresenter = event.getPresenter();
      displayCenterContent();
    }
    revealDisplay();
  }

  /**
   * Make sure the center content is the correct one
   */
  private void displayCenterContent() {
    if( centerPresenter == null )
      return;
    display.setCenterContent( centerPresenter.getWidget() );
    revealDisplay();
  }
}
