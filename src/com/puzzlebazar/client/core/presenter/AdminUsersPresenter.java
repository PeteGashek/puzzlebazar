package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.Place;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxy;
import com.puzzlebazar.client.core.proxy.AdminTabProxy;

/**
 * This is the presenter of the general tab in the administration page.
 * 
 * @author Philippe Beaudoin
 */
public class AdminUsersPresenter 
extends PresenterImpl<AdminUsersPresenter.MyDisplay, AdminUsersPresenter.MyProxy> {

  public interface MyDisplay extends Display { }

  public interface MyProxy extends TabContentProxy<AdminUsersPresenter>, Place {}

  @Inject
  public AdminUsersPresenter(final EventBus eventBus, 
      final MyDisplay display, 
      final MyProxy proxy ) {
    super(eventBus, display, proxy );
  }

  @Override
  protected void setContentInParent() {
    SetContentEvent.fire(eventBus, AdminTabProxy.TYPE_SetTabContent, this);
  }
}
