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
        final EventBus eventBus,
        final AsyncProvider<AdminGeneralPresenter> presenter,
        final Translations translations) {
      super(
          eventBus, 
          new CodeSplitProvider<AdminGeneralPresenter>(presenter, translations),
          AdminTabPresenter.TYPE_RequestTabs,
          0, // Priority
          translations.tabGeneral(),
          getNameTokenStatic() );
    }
  }
  
  @Inject
  public AdminGeneralProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final AsyncProvider<AdminGeneralPresenter> presenter,
      final WrappedProxy wrappedProxy,
      final CurrentUser currentUser) {
    super(
        eventBus, 
        placeManager,
        wrappedProxy,
        new AdminSecurePlace(getNameTokenStatic(), currentUser));
  }

}