package com.puzzlebazar.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.presenter.AppPresenter;
import com.puzzlebazar.client.resources.Resources;

public class AppView implements AppPresenter.Display {

  interface Binder extends UiBinder<Widget, AppView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField FlowPanel topBarContainer;
  @UiField FlowPanel linkColumnContainer;

  private final Widget panel;

  @UiField(provided = true)
  final Resources resources;
  
  @Inject
  public AppView( Resources resources ) {
    this.resources = resources;
    panel =  binder.createAndBindUi(this);
  }

  @Override
  public void setLinkColumn(Widget linkColumn) {
    linkColumnContainer.clear();
    linkColumnContainer.add( linkColumn );
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
