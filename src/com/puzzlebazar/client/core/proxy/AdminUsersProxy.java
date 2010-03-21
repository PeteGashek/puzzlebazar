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
import com.puzzlebazar.client.core.presenter.AdminTabPresenter;
import com.puzzlebazar.client.core.presenter.AdminUsersPresenter;
import com.puzzlebazar.client.resources.Translations;

/**
 * The {@link Proxy} for the users tab within the administration panel.
 * 
 * @author Philippe Beaudoin
 */
public class AdminUsersProxy extends TabContentProxyPlace<AdminUsersPresenter> 
implements AdminUsersPresenter.MyProxy {

  static private String getNameTokenStatic() {
    return NameTokens.getAdminUsers();
  }

  public static class WrappedProxy extends TabContentProxyImpl<AdminUsersPresenter> {
    @Inject
    public WrappedProxy(
        final AsyncProvider<AdminUsersPresenter> presenter,
        final Translations translations) {
      this.presenter = new CodeSplitProvider<AdminUsersPresenter>(presenter);
      this.requestTabsEventType = AdminTabPresenter.TYPE_RequestTabs;
      this.priority = 1;
      this.text = translations.tabUsers();
      this.historyToken = getNameTokenStatic();
    }
  }
  
  @Inject
  public AdminUsersProxy(
      final WrappedProxy wrappedProxy,
      final CurrentUser currentUser) {
    this.proxy = wrappedProxy;
    this.place = new AdminSecurePlace(getNameTokenStatic(), currentUser);    
  }

}