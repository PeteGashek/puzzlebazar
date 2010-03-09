package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.core.presenter.TopBarPresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.ui.TokenSeparatedList;

public class TopBarView implements TopBarPresenter.MyDisplay {

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
  public HasClickHandlers getSignIn() {
    return signIn;
  }

  @Override
  public HasClickHandlers getSignOut() {
    return signOut;
  }
  
}
