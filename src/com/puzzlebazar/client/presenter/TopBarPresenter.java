package com.puzzlebazar.client.presenter;

import com.philbeaudoin.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.puzzlebazar.client.presenter.event.LoginEvent;
import com.puzzlebazar.client.presenter.event.LoginHandler;
import com.puzzlebazar.shared.action.Login;
import com.puzzlebazar.shared.action.LoginResult;

public class TopBarPresenter extends BasicPresenter<TopBarPresenter.Display> implements LoginHandler {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
    public void setLoggedIn( String username );
    public void setLoggedOut();
    public HasClickHandlers getSignInButton();
  }

  private DispatchAsync dispatcher;

  @Inject
  public TopBarPresenter(final Display display, final EventBus eventBus,
      final DispatchAsync dispatcher) {
    super(display, eventBus);

    this.dispatcher = dispatcher;

    bind();
  }

  @Override
  protected void onBind() {
    display.setLoggedOut();
    
    registerHandler( display.getSignInButton().addClickHandler( new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doLogin();
      }
    }) );
    
    registerHandler( eventBus.addHandler( LoginEvent.getType(), this ) );     
    
  }

  @Override
  protected void onUnbind() {
  }

  @Override
  public void revealDisplay() {
  }

  /**
   * Performs the login
   */
  public void doLogin() {
    dispatcher.execute(new Login("MisterTest"),
        new AsyncCallback<LoginResult>() {

      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Login failed!");
      }

      @Override
      public void onSuccess(LoginResult result) {
        // take the result from the server and notify client interested
        // components
        eventBus.fireEvent(new LoginEvent(result.getLoginInfo()));
      }


    });    
  }

  @Override
  public void onLogin(LoginEvent event) {
    display.setLoggedIn( event.getLoginInfo().getUsername() );
  }

}
