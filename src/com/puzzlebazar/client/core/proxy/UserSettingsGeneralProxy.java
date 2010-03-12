/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceManager;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxyPlace;
import com.puzzlebazar.client.CodeSplitProvider;
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
        final EventBus eventBus,
        final AsyncProvider<UserSettingsGeneralPresenter> presenter,
        final Translations translations) {
      super(
          eventBus, 
          new CodeSplitProvider<UserSettingsGeneralPresenter>(presenter, translations),
          UserSettingsTabPresenter.TYPE_RequestTabs,
          0, // Priority
          translations.tabGeneral(),
          getNameTokenStatic() );
    }
  }
  
  @Inject
  public UserSettingsGeneralProxy(
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