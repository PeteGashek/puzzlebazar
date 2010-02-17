package com.puzzlebazar.client.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;

public class TopBarPresenter extends BasicPresenter<TopBarPresenter.Display> {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
  }

  private DispatchAsync dispatcher;

  @Inject
  public TopBarPresenter(final Display display, final EventBus eventBus,
      final DispatchAsync dispatcher) {
    super(display, eventBus);

    this.dispatcher = dispatcher;

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
