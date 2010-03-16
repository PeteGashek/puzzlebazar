package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.core.presenter.TopBarPresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.ui.TokenSeparatedList;

public class TopBarView implements TopBarPresenter.MyView {

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
  private Command signInCallback = null;

  @Inject
  public TopBarView( Resources resources ) {
    this.resources = resources;
    widget = binder.createAndBindUi(this);

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
  public void setSignIn( String uri, String title, Command signInCallback ) {
    // We directly override onClick, otherwise popup blockers will not allow the window to be created.
    this.signInCallback = signInCallback;
    signIn.getElement().setAttribute( "onClick", getWindowOpenJS( uri, title ) );
    registerSignInCallback();
  }

  /**
   * This method registers a top-level callback method inside the HTML page. This
   * method is used to notify the page of a successful login.
   * It's used in the webpage {@code ClosePopup.html}.
   */
  private native void registerSignInCallback() /*-{
      var self = this;
      $wnd.signInCallback = function() { self.@com.puzzlebazar.client.core.view.TopBarView::signInSuccessfull()() };
  }-*/;

  @SuppressWarnings("unused")
  private void signInSuccessfull() {
    if( signInCallback != null )
      this.signInCallback.execute();
  }
  
  @Override
  public HasClickHandlers getSignOut() {
    return signOut;
  }

  private static String getWindowOpenJS( String uri, String title) {
    return 
    "var w = window.open( '"+uri+"', '"+title+"', 'width=450,height=500,location=1,status=1,resizable=yes' );" +
    "w.moveTo( (window.innerWidth-450)/2, (window.innerHeight-500)/2 );";
  }

}
