package com.puzzlebazar.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.presenter.AppPresenter;
import com.puzzlebazar.client.resources.Resources;

public class AppView implements AppPresenter.Display {

  interface Binder extends UiBinder<Widget, AppView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField LayoutPanel topBarContainer;
  @UiField LayoutPanel mainContainer;

  private final Widget panel;

  
  @Inject
  public AppView( Resources resources ) {
    panel =  binder.createAndBindUi(this);
  }

  @Override
  public void setMain(Widget main) {
    mainContainer.clear();
    mainContainer.add( main );
  }

  @Override
  public void setTopBar(Widget topBar) {
    topBarContainer.clear();
    topBarContainer.add( topBar );
  }

  @Override
  public Widget asWidget() {
    return panel;
  }


}
