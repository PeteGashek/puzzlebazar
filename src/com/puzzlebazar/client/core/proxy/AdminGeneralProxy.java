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
  
  private static final String nameToken = "adgen";

  /**
   * A static method to access the name token of this proxy.
   * 
   * @return The name token.
   */
  public static final String getProxyNameToken() {
    return nameToken;
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
  public String getNameToken() {
    return nameToken;
  }
  
  @Override
  public float getPriority() {
    return 0;
  }
    
}