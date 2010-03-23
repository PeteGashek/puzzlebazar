package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.PagePresenter;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.ui.SquareGridLayoutPanel;
import com.puzzlebazar.client.util.SquareGridConverter;
import com.puzzlebazar.client.util.SquareGridValidator;
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
  private final SquareGridLayoutPanel puzzleContainer;
  private final Resources resources;
  private final SquareGridValidator squareGridValidator;
  private final SquareGridConverter squareGridConverter;
  private final Vec2i currentCell = new Vec2i(-1,-1);

  @UiField 
  FlowPanel topBarContainer;

  @UiField 
  LayoutPanel centerContainer;

  @UiField
  FocusPanel uiWidget;

  private Widget selectionWidget = null;

  @Inject
  public PuzzleView(SquareGridLayoutPanel puzzleContainer, Resources resources) {
    widget = binder.createAndBindUi(this);
    this.puzzleContainer = puzzleContainer;
    this.resources = resources;
    squareGridValidator = new SquareGridValidator( puzzleContainer );
    squareGridConverter = new SquareGridConverter( puzzleContainer, uiWidget );

    // TODO temp
    int width = 12;
    int height = 8;
    int border = 10;
    centerContainer.add( puzzleContainer );
    puzzleContainer.setBorder(border);
    puzzleContainer.setSize(width, height);
    puzzleContainer.createInnerEdges(1, resources.style().gray());
    puzzleContainer.createOuterEdges(3, resources.style().black());
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

  @UiHandler("uiWidget")
  void handleMouseMove(MouseMoveEvent event) {
    mouseMovedTo(event.getX(), event.getY());
  }

  @UiHandler("uiWidget")
  void handleMouseOut(MouseOutEvent event) {
    mouseMovedTo(event.getX(), event.getY());
  }

  @UiHandler("uiWidget")
  void handleMouseDown(MouseDownEvent event) {
    SquareGridConverter.VertexInfo vertexInfo = squareGridConverter.pixelToVertex(event.getX(), event.getY());
    if( squareGridValidator.isValidVertex( vertexInfo.vertex ) && vertexInfo.dist.max() < 10 ) {
      puzzleContainer.createVertex( vertexInfo.vertex, 12, 
          resources.style().blue() );  
      return;
    }

    SquareGridConverter.EdgeInfo edgeInfo = squareGridConverter.pixelToEdge(event.getX(), event.getY());
    if( edgeInfo.isVertical && 
        squareGridValidator.isValidVerticalEdge( edgeInfo.edge ) && edgeInfo.dist < 10 ) {
      puzzleContainer.createVerticalEdge( edgeInfo.edge, 8, 
          resources.style().black() );  
    }
    else if( !edgeInfo.isVertical && 
        squareGridValidator.isValidHorizontalEdge( edgeInfo.edge ) && edgeInfo.dist < 10 ) {
      puzzleContainer.createHorizontalEdge( edgeInfo.edge, 8, 
          resources.style().black() );  

    }
    else {
      Vec2i cell = squareGridConverter.pixelToCell(event.getX(), event.getY());
      if( squareGridValidator.isValidCell( cell ) ) {
        puzzleContainer.createCell( cell, resources.style().gray() );  
        return;
      }
    }
  }

  /**
   * Call whenever the mouse move, wither because of a 
   * {@link MouseMoveEvent} or a {@link MouseOutEvent}.
   * 
   * @param x The x pixel coordinate within {@link #uiWidget}.
   * @param y The y pixel coordinate within {@link #uiWidget}.
   */
  private void mouseMovedTo(int x, int y) {

    Vec2i cell = squareGridConverter.pixelToCell(x,y);
    if( squareGridValidator.isValidCell(cell) ) {
      if( !cell.equals(currentCell) ) {
        if( squareGridValidator.isValidCell(currentCell))
          onMouseOutCell(currentCell);
        currentCell.copy( cell );
        onMouseOverCell(currentCell);
      }
    } else {
      if( squareGridValidator.isValidCell(currentCell) )
        onMouseOutCell(currentCell);
      currentCell.x = currentCell.y = -1;
    }
  }

  /**
   * Called whenever the mouse moves out of a cell.
   * 
   * @param cell The coordinates of the cell being moved out of.
   */
  void onMouseOutCell(Vec2i cell) {
    if( selectionWidget != null )
      selectionWidget.removeFromParent();
    selectionWidget = null;
  }

  /**
   * Called whenever the mouse moves into a cell.
   * 
   * @param cell The coordinates of the cell being moved into.
   */
  void onMouseOverCell(Vec2i cell) {
    // TODO Temporary, to ensure onMouseOut is called correctly
    assert selectionWidget == null : "onMouseOut not called?";
    if( selectionWidget != null )
      selectionWidget.removeFromParent();
    selectionWidget  = puzzleContainer.createSelectedCell( cell, 
        resources.style().yellow(), resources.style().transparent() );    
  }



}
