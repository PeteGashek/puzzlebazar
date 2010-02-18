package com.puzzlebazar.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.puzzlebazar.client.presenter.event.NewMainDisplayEvent;



public class SplitMainPresenter extends BasicPresenter<SplitMainPresenter.Display> {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
    void setLinkColumn( Widget linkColumn );
  }

  private final LinkColumnPresenter linkColumnPresenter;

  @Inject
  public SplitMainPresenter(final Display display, final EventBus eventBus,       
      LinkColumnPresenter  linkColumnPresenter ) {
    super(display, eventBus);
    
    this.linkColumnPresenter = linkColumnPresenter;

    bind();
  }  

  @Override
  protected void onBind() {
    display.setLinkColumn( this.linkColumnPresenter.getWidget() );
  }

  @Override
  protected void onUnbind() {
    
  }

  @Override
  public void revealDisplay() {
    NewMainDisplayEvent.fire(eventBus, this);    
  }


}
