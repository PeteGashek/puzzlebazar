/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.CodeSplitProvider;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxyPlace;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsTabPresenter;
import com.puzzlebazar.client.resources.Translations;

/**
 * The {@link Proxy} for the general tab within the user settings panel.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsGeneralProxy 
extends TabContentProxyPlace<UserSettingsGeneralPresenter> 
implements UserSettingsGeneralPresenter.MyProxy {

  static private String getNameTokenStatic() {
    return NameTokens.getUserSettingsGeneral();
  }

  public static class WrappedProxy extends TabContentProxyImpl<UserSettingsGeneralPresenter> {
    @Inject
    public WrappedProxy(
        final AsyncProvider<UserSettingsGeneralPresenter> presenter,
        final Translations translations) {
      super.presenter = new CodeSplitProvider<UserSettingsGeneralPresenter>(presenter);
      super.requestTabsEventType = UserSettingsTabPresenter.TYPE_RequestTabs;
      super.priority = 0;
      super.text = translations.tabGeneral();
      super.historyToken = getNameTokenStatic();
    }
  }
  
  @Inject
  public UserSettingsGeneralProxy(
      final WrappedProxy wrappedProxy,
      final CurrentUser currentUser) {
    super.proxy = wrappedProxy;
    super.place = new LoggedInSecurePlace(getNameTokenStatic(), currentUser);
  }
  
}