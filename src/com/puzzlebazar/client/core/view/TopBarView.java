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

public class TopBarView implements TopBarPresenter.Display {

  interface Binder extends UiBinder<Widget, TopBarView> { }
  protected static final Binder binder = GWT.create(Binder.class);
  private final Widget widget;
  @Override public Widget asWidget() {
    return widget;
  }
  
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

  @Inject
  public TopBarView( Resources resources ) {
    this.resources = resources;
    widget = binder.createAndBindUi(this);  
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
    bar.setWidgetVisible( signIn, !loggedIn );
    bar.setWidgetVisible( username, loggedIn );
    bar.setWidgetVisible( settings, loggedIn );
    bar.setWidgetVisible( administration, loggedIn );
    bar.setWidgetVisible( signOut, loggedIn );
  }

  @Override
  public void setUserSettingsHistoryToken(String historyToken) {
    settings.setTargetHistoryToken( historyToken );
  }

  @Override
  public void setAdministrationHistoryToken(String historyToken) {
    administration.setTargetHistoryToken( historyToken );
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
