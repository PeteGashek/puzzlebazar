package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.dispatch.client.DispatchAsync;
import com.philbeaudoin.gwt.presenter.client.View;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.ViewInterface;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyInterface;
import com.puzzlebazar.shared.action.DoLogin;
import com.puzzlebazar.shared.action.DoLogout;
import com.puzzlebazar.shared.action.StringResult;
import com.puzzlebazar.shared.model.User;

public class TopBarPresenter extends PresenterImpl<TopBarPresenter.MyView,TopBarPresenter.MyProxy> 
implements CurrentUserInfoAvailableHandler {

  @ViewInterface
  public interface MyView extends View {
    public void setLoggedIn( String username, boolean isAdministrator );
    public void setLoggedOut();
    public HasClickHandlers getSignIn();
    public HasClickHandlers getSignOut();
  }

  @ProxyInterface
  public interface MyProxy extends Proxy<TopBarPresenter> {}

  private final DispatchAsync dispatcher;

  @Inject
  public TopBarPresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy,
      final DispatchAsync dispatcher ) {
    super(eventBus, view, proxy);

    this.dispatcher = dispatcher;
  }

  @Override
  protected void setContentInParent() {}
  
  @Override
  protected void onBind() {
    super.onBind();
    
    getView().setLoggedOut();

    registerHandler( getView().getSignIn().addClickHandler( new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doSignIn();
      }
    } ) );

    registerHandler( getView().getSignOut().addClickHandler( new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doSignOut();
      }
    } ) );

    registerHandler( eventBus.addHandler( CurrentUserInfoAvailableEvent.getType(), this ) );
  }

  @Override
  public void onCurrentUserInfoAvailable(CurrentUserInfoAvailableEvent event) {
    User userInfo = event.getUserInfo();
    getView().setLoggedIn( userInfo.getEmail(), userInfo.isAdministrator() );
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