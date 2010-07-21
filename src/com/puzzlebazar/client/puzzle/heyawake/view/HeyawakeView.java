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

package com.puzzlebazar.client.puzzle.heyawake.view;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.HandlerContainerImpl;
import com.puzzlebazar.client.puzzle.heyawake.presenter.HeyawakePresenter.MyView;
import com.puzzlebazar.client.puzzle.heyawake.presenter.HeyawakePresenter.ViewFactory;
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
import com.puzzlebazar.client.ui.VertexMouseDownEvent;
import com.puzzlebazar.client.ui.VertexMouseDownHandler;
import com.puzzlebazar.client.ui.VertexMouseOutEvent;
import com.puzzlebazar.client.ui.VertexMouseOutHandler;
import com.puzzlebazar.client.ui.VertexMouseOverEvent;
import com.puzzlebazar.client.ui.VertexMouseOverHandler;
import com.puzzlebazar.shared.util.Has2DSize;

public class HeyawakeView extends HandlerContainerImpl implements MyView,
VertexMouseOverHandler, VertexMouseOutHandler, 
EdgeMouseOverHandler, EdgeMouseOutHandler, 
CellMouseOverHandler, CellMouseOutHandler, 
CellMouseDownHandler, EdgeMouseDownHandler, 
VertexMouseDownHandler  {

  public static class FactoryImpl implements ViewFactory {
    private final SquareGridLayoutPanel puzzleWidget;
    private final Resources resources;
    private final SquareGridManipulator.Factory squareGridManipulatorFactory;
    
    @Inject
    public FactoryImpl(
        SquareGridLayoutPanel puzzleWidget,
        Resources resources,
        SquareGridManipulator.Factory squareGridManipulatorFactory) {
      this.puzzleWidget = puzzleWidget;
      this.resources = resources;
      this.squareGridManipulatorFactory = squareGridManipulatorFactory;
    }

    @Override
    public MyView create(
        Widget uiWidget,
        Has2DSize puzzleSize) {
      return new HeyawakeView(puzzleWidget, resources, squareGridManipulatorFactory, uiWidget, puzzleSize);
    }
    
  }

  private final SquareGridLayoutPanel puzzleWidget;
  private final Resources resources;
  private final SquareGridManipulator squareGridManipulator;

  private Widget selectionWidget = null;
  
  private HeyawakeView(
      SquareGridLayoutPanel puzzleWidget, 
      Resources resources,
      SquareGridManipulator.Factory squareGridManipulatorFactory,
      Widget uiWidget,
      Has2DSize puzzleSize ) {
    super(false); // No autobinding, the presenter will bind us.
    
    this.puzzleWidget = puzzleWidget;
    this.resources = resources;
    squareGridManipulator = squareGridManipulatorFactory.create( puzzleWidget, uiWidget );

    int width = puzzleSize.getWidth();
    int height = puzzleSize.getHeight();
    int border = 10;
    puzzleWidget.setBorder(border);
    puzzleWidget.setSize(width, height);
    puzzleWidget.createInnerEdges(1, resources.style().gray());
    puzzleWidget.createOuterEdges(3, resources.style().black());    
    
    squareGridManipulator.setVertexDistance( 6 );
    squareGridManipulator.setEdgeDistance( 4 );
  }
  
  @Override
  public void onBind() {
    super.onBind();

    registerHandler( squareGridManipulator.addVertexMouseOverHandler(this) );
    registerHandler( squareGridManipulator.addVertexMouseOutHandler(this) );
    registerHandler( squareGridManipulator.addEdgeMouseOverHandler(this) );
    registerHandler( squareGridManipulator.addEdgeMouseOutHandler(this) );
    registerHandler( squareGridManipulator.addCellMouseOverHandler(this) );
    registerHandler( squareGridManipulator.addCellMouseOutHandler(this) );
    registerHandler( squareGridManipulator.addCellMouseDownHandler(this) );
    registerHandler( squareGridManipulator.addEdgeMouseDownHandler(this) );
    registerHandler( squareGridManipulator.addVertexMouseDownHandler(this) );
    
  }

  public void onUnbind() {
    super.onUnbind();
    if( selectionWidget != null )
      selectionWidget.removeFromParent();
    selectionWidget = null;    
  }
  
  @Override
  public Widget asWidget() {
    return puzzleWidget;
  }
  
  @Override
  public void onVertexMouseOver(VertexMouseOverEvent event) {
    if( selectionWidget != null )
      selectionWidget.removeFromParent();
    selectionWidget  = puzzleWidget.createVertex( event.getVertex(), 12,
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
      selectionWidget = puzzleWidget.createVerticalEdge( event.getEdge(), 8, 
          resources.style().yellow(), resources.style().transparent() );
    else
      selectionWidget = puzzleWidget.createHorizontalEdge( event.getEdge(), 8, 
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
    selectionWidget  = puzzleWidget.createSelectedCell( event.getCell(), 
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
    puzzleWidget.createCell( event.getCell(), resources.style().gray() );  
  }

  @Override
  public void onEdgeMouseDown(EdgeMouseDownEvent event) {
    if( event.isVertical() )
      puzzleWidget.createVerticalEdge( event.getEdge(), 8, 
          resources.style().black() );
    else
      puzzleWidget.createHorizontalEdge( event.getEdge(), 8, 
          resources.style().black() );
  }

  @Override
  public void onVertexMouseDown(VertexMouseDownEvent event) {
    puzzleWidget.createVertex( event.getVertex(), 12, 
        resources.style().blue() );  
  }

  @Override
  public void addContent(Object slot, Widget content) {
  }

  @Override
  public void setContent(Object slot, Widget content) {
  }

  @Override
  public void removeContent(Object slot, Widget content) {
  }
}
