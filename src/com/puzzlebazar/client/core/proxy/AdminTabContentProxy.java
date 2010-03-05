package com.puzzlebazar.client.core.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.RequestTabsHandler;
import com.philbeaudoin.gwt.presenter.client.proxy.CallbackProvider;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxyImpl;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.resources.Translations;

public abstract class AdminTabContentProxy<P extends Presenter> extends
    TabContentProxyImpl<P> {

  private final CurrentUser currentUser;
  protected final Translations translations;

  public AdminTabContentProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final CallbackProvider<P> presenter, 
      final Type<RequestTabsHandler> requestTabsEventType,
      final CurrentUser currentUser,
      final Translations translations ) {
    super(eventBus, placeManager, presenter, requestTabsEventType);
    this.currentUser = currentUser;
    this.translations = translations;
  }

  @Override
  public boolean canReveal() {
    return currentUser.isAdministrator();
  }
  
}