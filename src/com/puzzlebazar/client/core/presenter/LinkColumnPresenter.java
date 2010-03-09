package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;

public class LinkColumnPresenter extends PresenterImpl<LinkColumnPresenter.MyDisplay, LinkColumnPresenter.MyProxy> {


  public interface MyDisplay extends Display {}

  public interface MyProxy extends Proxy<LinkColumnPresenter> {}
  
  @Inject
  public LinkColumnPresenter(
      final EventBus eventBus, 
      final MyDisplay display,  
      final MyProxy proxy ) {
    super(eventBus, display, proxy);
  }

  @Override
  protected void setContentInParent() {}

}
