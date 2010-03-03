package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.proxy.PresenterProxy;

public class LinkColumnPresenter extends PresenterImpl<LinkColumnPresenter.Display, LinkColumnPresenter.Proxy> {


  public interface Display extends PresenterDisplay {
  }

  public interface Proxy extends PresenterProxy {}
  
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
