package com.puzzlebazar.client.presenter;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.puzzlebazar.client.presenter.event.CurrentUserInfoAvailableEvent;
import com.puzzlebazar.client.presenter.event.CurrentUserInfoAvailableHandler;
import com.puzzlebazar.client.presenter.event.LoginURLsAvailableEvent;
import com.puzzlebazar.client.presenter.event.LoginURLsAvailableHandler;

public class TopBarPresenter extends BasicPresenter<TopBarPresenter.Display> implements LoginURLsAvailableHandler, CurrentUserInfoAvailableHandler {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
    public void setLoggedIn( String username );
    public void setLoggedOut();
    public void setSignInURL( String signInURL );
    public void setSignOutURL( String signOutURL );
    public HasClickHandlers getSignInButton();
  }
  
  @Inject
  public TopBarPresenter(final Display display, final EventBus eventBus) {
    super(display, eventBus);

    bind();
  }

  @Override
  protected void onBind() {
    display.setLoggedOut();
    
    registerHandler( eventBus.addHandler( LoginURLsAvailableEvent.getType(), this ));
    
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

  @Override
  public void onLoginURLsAvailable(LoginURLsAvailableEvent event) {
    display.setSignInURL( event.getLoginURL() );
    display.setSignOutURL( event.getLogoutURL() );
  }

}
