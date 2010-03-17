/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceManager;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxyPlace;
import com.puzzlebazar.client.CodeSplitProvider;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.core.presenter.UserSettingsAccountsPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsTabPresenter;
import com.puzzlebazar.client.resources.Translations;

/**
 * The {@link Proxy} for the accounts tab within the user settings panel.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsAccountsProxy 
extends TabContentProxyPlace<UserSettingsAccountsPresenter> 
implements UserSettingsAccountsPresenter.MyProxy {
  
  static private String getNameTokenStatic() {
    return NameTokens.getUserSettingsAccounts();
  }

  public static class WrappedProxy extends TabContentProxyImpl<UserSettingsAccountsPresenter> {
    @Inject
    public WrappedProxy(
        final EventBus eventBus,
        final AsyncProvider<UserSettingsAccountsPresenter> presenter,
        final Translations translations) {
      super(
          eventBus, 
          new CodeSplitProvider<UserSettingsAccountsPresenter>(presenter),
          UserSettingsTabPresenter.TYPE_RequestTabs,
          1, // Priority
          translations.tabAccounts(),
          getNameTokenStatic() );
    }
  }
  
  @Inject
  public UserSettingsAccountsProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final WrappedProxy wrappedProxy,
      final CurrentUser currentUser) {
    super(
        eventBus, 
        placeManager,
        wrappedProxy,
        new LoggedInSecurePlace(getNameTokenStatic(), currentUser));
  }
  
}