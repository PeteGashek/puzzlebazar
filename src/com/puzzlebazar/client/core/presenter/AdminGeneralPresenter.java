package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.philbeaudoin.gwtp.mvp.client.PresenterImpl;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.proxy.Place;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentHandler;
import com.philbeaudoin.gwtp.mvp.client.proxy.TabContentProxy;
import com.philbeaudoin.gwtp.mvp.rebind.ContentSlot;
import com.philbeaudoin.gwtp.mvp.rebind.NameToken;
import com.philbeaudoin.gwtp.mvp.rebind.PlaceInstance;
import com.philbeaudoin.gwtp.mvp.rebind.ProxyCodeSplit;
import com.philbeaudoin.gwtp.mvp.rebind.TabInfo;
import com.puzzlebazar.client.NameTokens;

/**
 * This is the presenter of the general tab in the administration page.
 * 
 * @author Philippe Beaudoin
 */
public class AdminGeneralPresenter 
extends PresenterImpl<AdminGeneralPresenter.MyView, AdminGeneralPresenter.MyProxy> {

  // TODO Remove, only for testing purposes right now.
  @ContentSlot
  public static final Type<RevealContentHandler<?>> TYPE_DummySetContent = new Type<RevealContentHandler<?>>();

  public interface MyView extends View { }

  @ProxyCodeSplit
  @NameToken( NameTokens.adminGeneral )
  @PlaceInstance( "new com.puzzlebazar.client.AdminSecurePlace(nameToken, ginjector.getCurrentUser())" )
  @TabInfo(
      container = AdminTabPresenter.class, 
      priority = 0, 
      getLabel="ginjector.getTranslations().tabGeneral()")
  public interface MyProxy extends TabContentProxy<AdminGeneralPresenter>, Place {}

  @Inject
  public AdminGeneralPresenter(final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy ) {
    super(eventBus, view, proxy );
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(eventBus, AdminTabPresenter.TYPE_RevealTabContent, this);
  }
}
