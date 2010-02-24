package com.puzzlebazar.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.presenter.SplitMainPresenter;
import com.puzzlebazar.client.resources.Resources;

public class SplitMainView implements SplitMainPresenter.Display {

  interface Binder extends UiBinder<Widget, SplitMainView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField FlowPanel sideBarContainer;
  @UiField FlowPanel centerContentContainer;

  private final Widget panel;

  @UiField(provided = true)
  final Resources resources;
  
  @Inject
  public SplitMainView( Resources resources ) {
    this.resources = resources;
    panel =  binder.createAndBindUi(this);
  }

  @Override
  public void setSideBarContent(Widget sideBarContent) {
    sideBarContainer.clear();
    sideBarContainer.add( sideBarContent );
  }

  @Override
  public void setCenterContent(Widget centerContent) {
    centerContentContainer.clear();
    centerContentContainer.add( centerContent );
  }

  @Override
  public Widget asWidget() {
    return panel;
  }


}
