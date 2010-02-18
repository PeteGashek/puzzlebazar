package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;

public class LinkColumnPresenter extends BasicPresenter<LinkColumnPresenter.Display> {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
  }


  @Inject
  public LinkColumnPresenter(final Display display, final EventBus eventBus) {
    super(display, eventBus);

    bind();
  }

  @Override
  protected void onBind() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void onUnbind() {
    // TODO Auto-generated method stub

  }

  @Override
  public void revealDisplay() {
    // TODO Auto-generated method stub

  }

}
