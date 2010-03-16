package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.platform.dispatch.client.DispatchAsync;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.resources.Translations;
import com.puzzlebazar.shared.action.Logout;
import com.puzzlebazar.shared.action.NoResult;
import com.puzzlebazar.shared.model.User;

public class TopBarPresenter extends PresenterImpl<TopBarPresenter.MyView,TopBarPresenter.MyProxy> 
implements CurrentUserChangedHandler {

  public interface MyView extends View {
    public void setLoggedIn( String username, boolean isAdministrator );
    public void setLoggedOut();
    public void setSignIn( String uri, String title, Command signInCallback );
    public HasClickHandlers getSignOut();
  }

  public interface MyProxy extends Proxy<TopBarPresenter> {}

  private final DispatchAsync dispatcher;
  private final CurrentUser currentUser;
  private final Translations translations;

  @Inject
  public TopBarPresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy,
      final DispatchAsync dispatcher,
      final CurrentUser currentUser,
      final Translations translations) {
    super(eventBus, view, proxy);

    this.dispatcher = dispatcher;
    this.currentUser = currentUser;
    this.translations = translations;
  }

  @Override
  protected void setContentInParent() {}

  @Override
  protected void onBind() {
    super.onBind();

    checkUserStatus();

    view.setSignIn( 
        "/openid/login?popup=true&provider=google", 
        translations.openIdPopupTitle(),
        new Command() {
      @Override
      public void execute() {
        currentUser.fetchUser();        
      }
    } );

    registerHandler( getView().getSignOut().addClickHandler( new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doSignOut();
      }
    } ) );

    registerHandler( eventBus.addHandler( CurrentUserChangedEvent.getType(), this ) );
  }

  private void checkUserStatus() {
    if( currentUser.isLoggedIn() ) {
      User user = currentUser.getUser();
      getView().setLoggedIn( user.getEmail(), user.isAdministrator() );
    }
    else
      getView().setLoggedOut();
  }

  @Override
  public void onCurrentUserChanged(CurrentUserChangedEvent event) {
    checkUserStatus();
  }

  public void doSignOut() {
    getView().setLoggedOut();
    dispatcher.execute( new Logout(), new AsyncCallback<NoResult>() {

      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Server connection error.");
      }

      @Override
      public void onSuccess(NoResult noResult) {

      }
    } ); 
  }



}