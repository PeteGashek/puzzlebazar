/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.CodeSplitProvider;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxyPlace;
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
        final AsyncProvider<UserSettingsAccountsPresenter> presenter,
        final Translations translations) {
      super.presenter = new CodeSplitProvider<UserSettingsAccountsPresenter>(presenter);
      super.requestTabsEventType = UserSettingsTabPresenter.TYPE_RequestTabs;
      super.priority = 1;
      super.text = translations.tabAccounts();
      super.historyToken = getNameTokenStatic();
    }
  }
  
  @Inject
  public UserSettingsAccountsProxy(
      final WrappedProxy wrappedProxy,
      final CurrentUser currentUser) {
    super.proxy = wrappedProxy;
    super.place = new LoggedInSecurePlace(getNameTokenStatic(), currentUser);
  }
  
}