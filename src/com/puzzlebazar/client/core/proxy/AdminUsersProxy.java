/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.puzzlebazar.client.CodeSplitProvider;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.core.presenter.AdminUsersPresenter;
import com.puzzlebazar.client.core.presenter.AdminPresenter;
import com.puzzlebazar.client.resources.Translations;

public class AdminUsersProxy extends AdminTabContentProxy<AdminUsersPresenter> 
implements AdminUsersPresenter.Proxy {
  
  private static final String nameToken = "admin-users";

  /**
   * A static method to access the name token of this proxy.
   * 
   * @return The name token.
   */
  public static final String getProxyNameToken() {
    return nameToken;
  }
  
  @Inject
  public AdminUsersProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final AsyncProvider<AdminUsersPresenter> presenter, 
      final CurrentUser currentUser,
      final Translations translations) {
    super(
        eventBus, 
        placeManager,
        new CodeSplitProvider<AdminUsersPresenter>(presenter, translations),
        AdminPresenter.TYPE_RequestTabs,    
        currentUser,
        translations);
  }

  @Override
  public String getText() {
    return translations.tabUsers();
  }
  
  @Override
  public String getNameToken() {
    return nameToken;
  }
  
  @Override
  public float getPriority() {
    return 1;
  }
    
}