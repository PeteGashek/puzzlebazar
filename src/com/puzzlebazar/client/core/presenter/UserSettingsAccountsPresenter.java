package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.Place;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxy;

/**
 * This is the presenter of the accounts tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsAccountsPresenter extends PresenterImpl<UserSettingsAccountsPresenter.MyView, UserSettingsAccountsPresenter.MyProxy> {

  public interface MyView extends View { }

  public interface MyProxy extends TabContentProxy<UserSettingsAccountsPresenter>, Place {}

  @Inject
  public UserSettingsAccountsPresenter(
      final EventBus eventBus, 
      final MyView view,  
      final MyProxy proxy ) {
    super(
        eventBus, 
        view, 
        proxy );
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(eventBus, UserSettingsTabPresenter.TYPE_RevealTabContent, this);
  }
  
  @Override
  public void onReveal() {
    super.onReveal();
    DisplayShortMessageEvent.fireMessage(eventBus, "Welcome to your connected accounts page!" );
  }

  @Override
  public void onHide() {
    super.onHide();
    DisplayShortMessageEvent.fireClearMessage(eventBus);
  }
}
