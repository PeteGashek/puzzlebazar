package com.puzzlebazar.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.presenter.TopBarPresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.ui.TokenSeparatedList;

public class TopBarView implements TopBarPresenter.Display {

  interface Binder extends UiBinder<Widget, TopBarView> { }
  private static final Binder binder = GWT.create(Binder.class);

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

  private final Widget widget;

  private boolean loggedIn = false;
  private String signInURL = null;
  private String signOutURL = null;
  
  @Inject
  public TopBarView( Resources resources ) {
    this.resources = resources;
    widget =  binder.createAndBindUi(this);
    signIn.setHref("");
    signOut.setHref("");
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void setLoggedIn(String username) {
    this.username.setText(username);
    loggedIn  = true;
    setLoggedInVisibility();
  }

  @Override
  public void setLoggedOut() {
    username.setText("");
    loggedIn = false;
    setLoggedInVisibility();
  }

  private void setLoggedInVisibility() {
    bar.setWidgetVisible( notSignedIn, !loggedIn );
    bar.setWidgetVisible( signIn, !loggedIn && signInURL != null );
    bar.setWidgetVisible( username, loggedIn );
    bar.setWidgetVisible( settings, loggedIn );
    bar.setWidgetVisible( administration, loggedIn );
    bar.setWidgetVisible( signOut, loggedIn && signOutURL != null );
  }

  @Override
  public HasClickHandlers getSignInButton() {
    return signIn;
  }

  @Override
  public void setSignInURL(String signInURL) {
    this.signInURL = signInURL;
    signIn.setHref( signInURL );
    setLoggedInVisibility();
  }

  @Override
  public void setSignOutURL(String signOutURL) {
    this.signOutURL = signOutURL;
    signOut.setHref( signOutURL );
    setLoggedInVisibility();
  }

}
