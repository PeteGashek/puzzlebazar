package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.BasicPresenterWrapper;

public class LinkColumnPresenter extends BasicPresenter<LinkColumnPresenter.Display, LinkColumnPresenter.Wrapper> {

  public interface Display extends PresenterDisplay {
  }
  
  public static class Wrapper extends BasicPresenterWrapper<LinkColumnPresenter> {
    @Inject
    public Wrapper(EventBus eventBus, Provider<LinkColumnPresenter> presenter) {
      super(eventBus, presenter);
    }
  }

  @Inject
  public LinkColumnPresenter(final Display display, final EventBus eventBus, 
      final Wrapper wrapper ) {
    super(display, eventBus, wrapper, null);

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

}
