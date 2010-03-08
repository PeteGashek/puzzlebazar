/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxyPlace;
import com.puzzlebazar.client.CodeSplitProvider;
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
        final EventBus eventBus,
        final AsyncProvider<AdminUsersPresenter> presenter,
        final Translations translations) {
      super(
          eventBus, 
          new CodeSplitProvider<AdminUsersPresenter>(presenter, translations),
          AdminTabPresenter.TYPE_RequestTabs,
          1, // Priority
          translations.tabUsers(),
          getNameTokenStatic() );
    }
  }
  
  @Inject
  public AdminUsersProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final WrappedProxy wrappedProxy,
      final CurrentUser currentUser) {
    super(
        eventBus, 
        placeManager,
        wrappedProxy,
        new AdminSecurePlace(getNameTokenStatic(), currentUser));
  }

}