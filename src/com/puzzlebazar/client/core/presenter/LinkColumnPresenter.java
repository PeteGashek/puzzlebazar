package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.View;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.ViewInterface;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyInterface;

public class LinkColumnPresenter extends PresenterImpl<LinkColumnPresenter.MyView, LinkColumnPresenter.MyProxy> {

  @ViewInterface
  public interface MyView extends View {}

  @ProxyInterface
  public interface MyProxy extends Proxy<LinkColumnPresenter> {}
  
  @Inject
  public LinkColumnPresenter(
      final EventBus eventBus, 
      final MyView view,  
      final MyProxy proxy ) {
    super(eventBus, view, proxy);
  }

  @Override
  protected void setContentInParent() {}

}
