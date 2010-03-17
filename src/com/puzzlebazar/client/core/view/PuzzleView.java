package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;

/**
 * The {@link com.philbeaudoin.platform.mvp.client.View} for {@link PagePresenter}.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzleView implements PuzzlePresenter.MyView {

  interface Binder extends UiBinder<DockLayoutPanel, PuzzleView> { }
  protected static final Binder binder = GWT.create(Binder.class);

  private final DockLayoutPanel widget;
  
  @UiField 
  FlowPanel topBarContainer;
  
  @Inject
  public PuzzleView() {
    widget = binder.createAndBindUi(this);
  }
  
  @Override 
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void setTopBarContent(Widget topBarContent) {
    topBarContainer.clear();
    topBarContainer.add( topBarContent );
  }


}
