/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.puzzlebazar.client.CodeSplitProvider;
import com.puzzlebazar.client.core.presenter.AdminGeneralPresenter;
import com.puzzlebazar.client.core.presenter.AdminPresenter;
import com.puzzlebazar.client.resources.Translations;

public class AdminGeneralProxy extends TabContentProxyImpl<AdminGeneralPresenter> 
implements AdminGeneralPresenter.Proxy {
  
  private static final String historyToken = "adgen";

  /**
   * A static method to access the history token of this proxy
   * 
   * @return The history token.
   */
  public static final String getProxyHistoryToken() {
    return historyToken;
  }
  
  private final Translations translations;  
  
  @Inject
  public AdminGeneralProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final AsyncProvider<AdminGeneralPresenter> presenter, 
      final Translations translations) {
    super(
        eventBus, 
        placeManager, 
        new CodeSplitProvider<AdminGeneralPresenter>(presenter, translations),
        AdminPresenter.TYPE_RequestTabs);
    
    this.translations = translations;
  }

  @Override
  public String getText() {
    return translations.tabGeneral();
  }
  
  @Override
  public String getHistoryToken() {
    return historyToken;
  }
  
  @Override
  public float getPriority() {
    return 0;
  }
    
}