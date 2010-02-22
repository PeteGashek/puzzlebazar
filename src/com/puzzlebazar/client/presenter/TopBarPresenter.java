package com.puzzlebazar.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.dispatch.client.DispatchAsync;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.puzzlebazar.client.place.UserSettingsPresenterPlace;
import com.puzzlebazar.client.presenter.event.CurrentUserInfoAvailableEvent;
import com.puzzlebazar.client.presenter.event.CurrentUserInfoAvailableHandler;
import com.puzzlebazar.shared.action.DoLogin;
import com.puzzlebazar.shared.action.DoLogout;
import com.puzzlebazar.shared.action.StringResult;

public class TopBarPresenter extends BasicPresenter<TopBarPresenter.Display> implements CurrentUserInfoAvailableHandler {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
    public void setLoggedIn( String username );
    public void setLoggedOut();
    public HasClickHandlers getSignIn();
    public HasClickHandlers getSignOut();
    public void setUserSettingsHistoryToken(String historyToken);
  }

  private final DispatchAsync dispatcher;

  @Inject
  public TopBarPresenter(final Display display, final EventBus eventBus,
      final DispatchAsync dispatcher,
      final UserSettingsPresenterPlace userSettingsPresenterPlace ) {
    super(display, eventBus);

    this.dispatcher = dispatcher;
    display.setUserSettingsHistoryToken( userSettingsPresenterPlace.getName() );

    bind();
  }

  @Override
  protected void onBind() {
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
  protected void onUnbind() {
  }

  @Override
  public void revealDisplay() {
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