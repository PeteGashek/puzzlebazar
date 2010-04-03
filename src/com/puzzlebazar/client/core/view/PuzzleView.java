package com.puzzlebazar.client.core.view;

/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.PagePresenter;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.ui.CellMouseDownEvent;
import com.puzzlebazar.client.ui.CellMouseDownHandler;
import com.puzzlebazar.client.ui.CellMouseOutEvent;
import com.puzzlebazar.client.ui.CellMouseOutHandler;
import com.puzzlebazar.client.ui.CellMouseOverEvent;
import com.puzzlebazar.client.ui.CellMouseOverHandler;
import com.puzzlebazar.client.ui.EdgeMouseDownEvent;
import com.puzzlebazar.client.ui.EdgeMouseDownHandler;
import com.puzzlebazar.client.ui.EdgeMouseOutEvent;
import com.puzzlebazar.client.ui.EdgeMouseOutHandler;
import com.puzzlebazar.client.ui.EdgeMouseOverEvent;
import com.puzzlebazar.client.ui.EdgeMouseOverHandler;
import com.puzzlebazar.client.ui.SquareGridLayoutPanel;
import com.puzzlebazar.client.ui.SquareGridManipulator;
import com.puzzlebazar.client.ui.SquareGridManipulatorFactory;
import com.puzzlebazar.client.ui.VertexMouseDownEvent;
import com.puzzlebazar.client.ui.VertexMouseDownHandler;
import com.puzzlebazar.client.ui.VertexMouseOutEvent;
import com.puzzlebazar.client.ui.VertexMouseOutHandler;
import com.puzzlebazar.client.ui.VertexMouseOverEvent;
import com.puzzlebazar.client.ui.VertexMouseOverHandler;

/**
 * The {@link com.philbeaudoin.gwtp.mvp.client.View} for {@link PagePresenter}.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzleView extends ViewImpl implements PuzzlePresenter.MyView,
VertexMouseOverHandler, VertexMouseOutHandler, 
EdgeMouseOverHandler, EdgeMouseOutHandler, 
CellMouseOverHandler, CellMouseOutHandler, 
CellMouseDownHandler, EdgeMouseDownHandler, VertexMouseDownHandler
{

  interface Binder extends UiBinder<DockLayoutPanel, PuzzleView> { }
  protected static final Binder binder = GWT.create(Binder.class);

  private final DockLayoutPanel widget;
  private final SquareGridLayoutPanel puzzleContainer;
  private final Resources resources;

  @UiField 
  FlowPanel topBarContainer;

  @UiField 
  LayoutPanel centerContainer;

  @UiField
  HTML uiWidget;

  private Widget selectionWidget = null;

  @Inject
  public PuzzleView(
      SquareGridLayoutPanel puzzleContainer, 
      Resources resources,
      SquareGridManipulatorFactory squareGridManipulatorFactory ) {
    widget = binder.createAndBindUi(this);
    this.puzzleContainer = puzzleContainer;
    this.resources = resources;
    SquareGridManipulator squareGridManipulator = squareGridManipulatorFactory.create( puzzleContainer, uiWidget );

    // TODO temp
    int width = 10;
    int height = 10;
    int border = 10;
    centerContainer.add( puzzleContainer );
    puzzleContainer.setBorder(border);
    puzzleContainer.setSize(width, height);
    puzzleContainer.createInnerEdges(1, resources.style().gray());
    puzzleContainer.createOuterEdges(3, resources.style().black());    
    
    squareGridManipulator.addVertexMouseOverHandler(this);
    squareGridManipulator.addVertexMouseOutHandler(this);
    squareGridManipulator.addEdgeMouseOverHandler(this);
    squareGridManipulator.addEdgeMouseOutHandler(this);
    squareGridManipulator.addCellMouseOverHandler(this);
    squareGridManipulator.addCellMouseOutHandler(this);
    squareGridManipulator.addCellMouseDownHandler(this);
    squareGridManipulator.addEdgeMouseDownHandler(this);
    squareGridManipulator.addVertexMouseDownHandler(this);
    
    squareGridManipulator.setVertexDistance( 6 );
    squareGridManipulator.setEdgeDistance( 4 );
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


  @Override
  public void onVertexMouseOver(VertexMouseOverEvent event) {
    if( selectionWidget != null )
      selectionWidget.removeFromParent();
    selectionWidget  = puzzleContainer.createVertex( event.getVertex(), 12,
        resources.style().yellow(), resources.style().transparent() );   
  }

  @Override
  public void onVertexMouseOut(VertexMouseOutEvent event) {
    if( selectionWidget != null )
      selectionWidget.removeFromParent();
    selectionWidget = null;    
  }

  @Override
  public void onEdgeMouseOver(EdgeMouseOverEvent event) {
    if( selectionWidget != null )
      selectionWidget.removeFromParent();
    if( event.isVertical() )
      selectionWidget = puzzleContainer.createVerticalEdge( event.getEdge(), 8, 
          resources.style().yellow(), resources.style().transparent() );
    else
      selectionWidget = puzzleContainer.createHorizontalEdge( event.getEdge(), 8, 
          resources.style().yellow(), resources.style().transparent() );
  }

  @Override
  public void onEdgeMouseOut(EdgeMouseOutEvent event) {
    if( selectionWidget != null )
      selectionWidget.removeFromParent();
    selectionWidget = null;    
  }

  @Override
  public void onCellMouseOver(CellMouseOverEvent event) {
    if( selectionWidget != null )
      selectionWidget.removeFromParent();
    selectionWidget  = puzzleContainer.createSelectedCell( event.getCell(), 
        resources.style().yellow(), resources.style().transparent() );    
  }

  @Override
  public void onCellMouseOut(CellMouseOutEvent event) {
    if( selectionWidget != null )
      selectionWidget.removeFromParent();
    selectionWidget = null;    
  }
  
  @Override
  public void onCellMouseDown(CellMouseDownEvent event) {
    puzzleContainer.createCell( event.getCell(), resources.style().gray() );  
  }

  @Override
  public void onEdgeMouseDown(EdgeMouseDownEvent event) {
    if( event.isVertical() )
      puzzleContainer.createVerticalEdge( event.getEdge(), 8, 
          resources.style().black() );
    else
      puzzleContainer.createHorizontalEdge( event.getEdge(), 8, 
          resources.style().black() );
  }

  @Override
  public void onVertexMouseDown(VertexMouseDownEvent event) {
    puzzleContainer.createVertex( event.getVertex(), 12, 
        resources.style().blue() );  
  }

}
