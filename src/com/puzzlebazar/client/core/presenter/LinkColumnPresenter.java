package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.philbeaudoin.gwtp.mvp.client.PresenterImpl;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.proxy.Proxy;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentEvent;

public class LinkColumnPresenter extends PresenterImpl<LinkColumnPresenter.MyView, LinkColumnPresenter.MyProxy> {

  public interface MyView extends View {}

  public interface MyProxy extends Proxy<LinkColumnPresenter> {}
  
  @Inject
  public LinkColumnPresenter(
      final EventBus eventBus, 
      final MyView view,  
      final MyProxy proxy ) {
    super(eventBus, view, proxy);
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(eventBus, SplitMainPresenter.TYPE_RevealSideBarContent, this);
  }

}
