package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.PagePresenter;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.ui.SquareGridLayoutPanel;
import com.puzzlebazar.client.util.Vec2i;

/**
 * The {@link com.philbeaudoin.platform.mvp.client.View} for {@link PagePresenter}.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzleView extends ViewImpl implements PuzzlePresenter.MyView {

  interface Binder extends UiBinder<DockLayoutPanel, PuzzleView> { }
  protected static final Binder binder = GWT.create(Binder.class);

  private final DockLayoutPanel widget;

  @UiField 
  FlowPanel topBarContainer;

  @UiField 
  LayoutPanel centerContainer;
  
  @Inject
  public PuzzleView(SquareGridLayoutPanel puzzleContainer, Resources resources) {
    widget = binder.createAndBindUi(this);
    int width = 12;
    int height = 8;
    int border = 10;
    centerContainer.add( puzzleContainer );
    puzzleContainer.setBorder(border);
    puzzleContainer.setSize(width, height);
    
    puzzleContainer.createInnerEdges(1, resources.style().gray());
    puzzleContainer.createOuterEdges(3, resources.style().black());
    puzzleContainer.createVertex( new Vec2i(0,0), 5, resources.style().gray() );
    puzzleContainer.createHorizontalEdge( new Vec2i(0,0), 5, resources.style().black() );
    puzzleContainer.createVerticalEdge( new Vec2i(1,0), 3, resources.style().gray() );
    puzzleContainer.createHorizontalEdge( new Vec2i(0,1), 5, resources.style().black() );
    puzzleContainer.createSelectedCell( new Vec2i(0,0), resources.style().yellow(), resources.style().transparent() );
  }

  @Override 
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void setContent(Object slot, Widget content) {
    if( slot == PuzzlePresenter.TYPE_RevealTopBarContent ) 
      setTopBarContent( content );
    else
      super.setContent(slot, content);
  }
    
  private void setTopBarContent(Widget topBarContent) {
    topBarContainer.clear();
    topBarContainer.add( topBarContent );
  }


}
