package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;

public class LinkColumnPresenter extends PresenterImpl<LinkColumnPresenter.Display, LinkColumnPresenter.Proxy> {


  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
  }

  public interface Proxy extends com.philbeaudoin.gwt.presenter.client.proxy.Proxy {}
  
  @Inject
  public LinkColumnPresenter(
      final EventBus eventBus, 
      final Provider<Display> display,  
      final Proxy proxy ) {
    super(eventBus, display, proxy, null);
  }

  @Override
  public void onBind() {
    super.onBind();
  }

  @Override
  public void onUnbind() {
    super.onBind();
  }

}
