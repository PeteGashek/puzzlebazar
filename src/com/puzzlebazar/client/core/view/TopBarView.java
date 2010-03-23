package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.TopBarPresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.ui.TokenSeparatedList;

public class TopBarView extends ViewImpl implements TopBarPresenter.MyView {

  interface Binder extends UiBinder<Widget, TopBarView> { }
  protected static final Binder binder = GWT.create(Binder.class);

  private final Widget widget;

  @UiField(provided = true)
  final Resources resources;

  @UiField
  TokenSeparatedList bar;

  @UiField
  InlineLabel notSignedIn;

  @UiField
  Anchor signIn;

  @UiField
  InlineLabel username;

  @UiField
  InlineHyperlink settings;

  @UiField
  InlineHyperlink administration;

  @UiField
  Anchor signOut;

  private boolean loggedIn = false;
  private boolean isAdministrator = false;
  private Command postSignInCallback = null;

  @Inject
  public TopBarView( Resources resources ) {
    this.resources = resources;
    widget = binder.createAndBindUi(this);
    signIn.getElement().setPropertyJSO( "onclick", getSignInFunction( "/openid/login?popup=true&provider=google" )  );
    registerPostSignInCallback();
  }

  @Override 
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void setLoggedIn(String username, boolean isAdministrator ) {
    this.username.setText(username);
    loggedIn  = true;
    this.isAdministrator = isAdministrator;
    setLoggedInVisibility();
  }

  @Override
  public void setLoggedOut() {
    username.setText("");
    loggedIn = false;
    isAdministrator = false;
    setLoggedInVisibility();
  }

  private void setLoggedInVisibility() {
    bar.setWidgetVisible( notSignedIn, !loggedIn );
    bar.setWidgetVisible( signIn, !loggedIn );
    bar.setWidgetVisible( username, loggedIn );
    bar.setWidgetVisible( settings, loggedIn );
    bar.setWidgetVisible( administration, loggedIn && isAdministrator );
    bar.setWidgetVisible( signOut, loggedIn );
  }

  @Override
  public void setPostSignInCallback( Command postSignInCallback ) {
    // We directly override onClick, otherwise popup blockers will not allow the window to be created.
    this.postSignInCallback = postSignInCallback;
  }
  
  @Override
  public HasClickHandlers getSignOut() {
    return signOut;
  }
  
  /**
   * This method returns a javascript function that should be called when
   * the signIn button is clicked. This is used instead of standard GWT
   * event handlers because otherwise the 'window.open()' call is
   * intercepted by popup blockers.
   * 
   * @param uri The URI where to direct the newly opened window.
   */
  private native JavaScriptObject getSignInFunction( String uri ) /*-{
    return function() { 
      var w = $wnd.open( uri, '', 'width=450,height=500,location=1,status=1,resizable=yes' );
      var width = @com.google.gwt.user.client.Window::getClientWidth()();
      var height = @com.google.gwt.user.client.Window::getClientHeight()();
      w.moveTo( (width-450)/2, (height-500)/2 );      
    };
  }-*/;

  /**
   * This method registers a top-level callback method inside the HTML page. This
   * method is used to notify the page of a successful login.
   * It's used in the webpage {@code ClosePopup.html}.
   */
  private native void registerPostSignInCallback() /*-{
      var self = this;
      $wnd.signInCallback = function() { self.@com.puzzlebazar.client.core.view.TopBarView::signInSuccessfull()() };
  }-*/;


  @SuppressWarnings("unused")
  private void signInSuccessfull() {
    if( postSignInCallback != null )
      this.postSignInCallback.execute();
  }

}
