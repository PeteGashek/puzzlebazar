package com.puzzlebazar.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.dispatch.client.DispatchAsync;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.proxy.PresenterProxy;
import com.puzzlebazar.client.presenter.event.CurrentUserInfoAvailableEvent;
import com.puzzlebazar.client.presenter.event.CurrentUserInfoAvailableHandler;
import com.puzzlebazar.client.proxy.UserSettingsMainProxy;
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
  }

  public interface Proxy extends PresenterProxy {}

  private final DispatchAsync dispatcher;

  @Inject
  public TopBarPresenter(final EventBus eventBus, final Display display, final Proxy proxy,
      final DispatchAsync dispatcher,
      final UserSettingsMainProxy userSettingsMainPresenterPlace ) {
    super(eventBus, display, proxy, null);

    this.dispatcher = dispatcher;
    display.setUserSettingsHistoryToken( userSettingsMainPresenterPlace.getHistoryToken() );
  }

  @Override
  public void onBind() {
    super.onBind();
    
    display.setLoggedOut();

    registerHandler( display.getSignIn().addClickHandler( new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doSignIn();
      }
    } ) );

    registerHandler( display.getSignOut().addClickHandler( new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doSignOut();
      }
    } ) );

    registerHandler( eventBus.addHandler( CurrentUserInfoAvailableEvent.getType(), this ) );
  }

  @Override
  public void onCurrentUserInfoAvailable(CurrentUserInfoAvailableEvent event) {
    display.setLoggedIn( event.getUserInfo().getEmail() );
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