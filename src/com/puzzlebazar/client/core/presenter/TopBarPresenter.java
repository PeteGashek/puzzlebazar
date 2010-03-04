package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.dispatch.client.DispatchAsync;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.proxy.PresenterProxy;
import com.puzzlebazar.client.core.proxy.AdminGeneralProxy;
import com.puzzlebazar.client.core.proxy.UserSettingsGeneralProxy;
import com.puzzlebazar.shared.action.DoLogin;
import com.puzzlebazar.shared.action.DoLogout;
import com.puzzlebazar.shared.action.StringResult;

public class TopBarPresenter extends PresenterImpl<TopBarPresenter.Display,TopBarPresenter.Proxy> 
implements CurrentUserInfoAvailableHandler {

  public interface Display extends PresenterDisplay {
    public void setLoggedIn( String username );
    public void setLoggedOut();
    public HasClickHandlers getSignIn();
    public HasClickHandlers getSignOut();
    public void setUserSettingsHistoryToken(String historyToken);
    public void setAdministrationHistoryToken(String proxyHistoryToken);
  }

  public interface Proxy extends PresenterProxy {}

  private final DispatchAsync dispatcher;

  @Inject
  public TopBarPresenter(
      final EventBus eventBus, 
      final Provider<Display> display, 
      final Proxy proxy,
      final DispatchAsync dispatcher ) {
    super(eventBus, display, proxy, null);

    this.dispatcher = dispatcher;
    getDisplay().setUserSettingsHistoryToken( UserSettingsGeneralProxy.getProxyNameToken() );
    getDisplay().setAdministrationHistoryToken( AdminGeneralProxy.getProxyNameToken() );
  }

  @Override
  public void onBind() {
    super.onBind();
    
    getDisplay().setLoggedOut();

    registerHandler( getDisplay().getSignIn().addClickHandler( new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doSignIn();
      }
    } ) );

    registerHandler( getDisplay().getSignOut().addClickHandler( new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doSignOut();
      }
    } ) );

    registerHandler( eventBus.addHandler( CurrentUserInfoAvailableEvent.getType(), this ) );
  }

  @Override
  public void onCurrentUserInfoAvailable(CurrentUserInfoAvailableEvent event) {
    getDisplay().setLoggedIn( event.getUserInfo().getEmail() );
  }

  public void doSignIn() {
    String url = Window.Location.getHref();

    dispatcher.execute( new DoLogin(url), new AsyncCallback<StringResult>() {

      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Server connection error.");
      }

      @Override
      public void onSuccess(StringResult result) {
        Window.Location.assign( result.getString() );
      }
    } );
  }

  public void doSignOut() {
    String url = Window.Location.getHref();

    dispatcher.execute( new DoLogout(url), new AsyncCallback<StringResult>() {

      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Server connection error.");
      }

      @Override
      public void onSuccess(StringResult result) {
        Window.Location.assign( result.getString() );
      }
    } );
  }

  
  
}