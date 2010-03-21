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
import com.puzzlebazar.client.core.presenter.AdminGeneralPresenter;
import com.puzzlebazar.client.core.presenter.AdminTabPresenter;
import com.puzzlebazar.client.resources.Translations;

/**
 * The {@link Proxy} for the general tab within the administration panel.
 * 
 * @author Philippe Beaudoin
 */
public class AdminGeneralProxy extends TabContentProxyPlace<AdminGeneralPresenter>
implements AdminGeneralPresenter.MyProxy {

  static private String getNameTokenStatic() {
    return NameTokens.getAdminGeneral();
  }

  public static class WrappedProxy extends TabContentProxyImpl<AdminGeneralPresenter> {
    @Inject
    public WrappedProxy(
        final AsyncProvider<AdminGeneralPresenter> presenter,
        final Translations translations) {
      this.presenter = new CodeSplitProvider<AdminGeneralPresenter>(presenter);
      this.requestTabsEventType = AdminTabPresenter.TYPE_RequestTabs;
      this.priority = 0;
      this.text = translations.tabGeneral();
      this.historyToken = getNameTokenStatic();
    }
  }
  
  @Inject
  public AdminGeneralProxy( WrappedProxy wrappedProxy, CurrentUser currentUser ) {
    this.proxy = wrappedProxy;
    this.place = new AdminSecurePlace(getNameTokenStatic(), currentUser);    
  }
  
}