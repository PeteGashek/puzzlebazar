package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.philbeaudoin.gwtp.mvp.client.PresenterImpl;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.proxy.Place;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.gwtp.mvp.client.proxy.TabContentProxy;
import com.philbeaudoin.gwtp.mvp.rebind.NameToken;
import com.philbeaudoin.gwtp.mvp.rebind.PlaceInstance;
import com.philbeaudoin.gwtp.mvp.rebind.ProxyCodeSplit;
import com.philbeaudoin.gwtp.mvp.rebind.TabInfo;
import com.puzzlebazar.client.NameTokens;

/**
 * This is the presenter of the accounts tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsAccountsPresenter extends PresenterImpl<UserSettingsAccountsPresenter.MyView, UserSettingsAccountsPresenter.MyProxy> {

  public interface MyView extends View { }

  @ProxyCodeSplit
  @NameToken( NameTokens.userSettingsAccounts )
  @PlaceInstance( "new com.puzzlebazar.client.LoggedInSecurePlace(nameToken, ginjector.getCurrentUser())" )
  @TabInfo(
      container = UserSettingsTabPresenter.class, 
      priority = 1,
      getLabel="ginjector.getTranslations().tabAccounts()")
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
