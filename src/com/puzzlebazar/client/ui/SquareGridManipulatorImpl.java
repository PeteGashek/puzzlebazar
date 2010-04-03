package com.puzzlebazar.client.ui;

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


import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.puzzlebazar.client.util.EdgeHitInfo;
import com.puzzlebazar.client.util.SquareGridConverter;
import com.puzzlebazar.client.util.VertexHitInfo;
import com.puzzlebazar.shared.util.SquareGridValidator;
import com.puzzlebazar.shared.util.Vec2i;


public class SquareGridManipulatorImpl 
implements MouseDownHandler, MouseUpHandler, MouseMoveHandler, 
MouseOutHandler, SquareGridManipulator {

  private final SquareGridValidator squareGridValidator;
  private final SquareGridConverter squareGridConverter;
  private final Widget uiWidget;

  private HandlerManager handlerManager = null;

  /**
   * Indicates which object the mouse is currently over.
   */
  private static final int OVER_NOTHING = -1;
  private static final int OVER_CELL = 0;
  private static final int OVER_EDGE = 1;
  private static final int OVER_VERTEX = 2;
  private int current = OVER_NOTHING;
  private boolean currentVertical = false;
  private Vec2i currentLoc = null;
 
  private HandlerRegistration mouseDownHandlerRegistration = null;
  private HandlerRegistration mouseUpHandlerRegistration = null;
  private HandlerRegistration mouseMoveHandlerRegistration = null;
  private HandlerRegistration mouseOutHandlerRegistration = null;
  
  private int vertexClickDistance = -1;
  private int vertexMoveDistance  = -1;
  private int edgeClickDistance   = -1;
  private int edgeMoveDistance    = -1;
    
  /**
   * Create a square grid manipulator. This constructor is called
   * from the {@link SquareGridManipulatorFactoryImpl}.
   */
  public SquareGridManipulatorImpl(
      SquareGridValidator squareGridValidator,
      SquareGridConverter squareGridConverter,
      SquareGridLayoutPanel gridPanel,
      Widget uiWidget ) {
    this.squareGridValidator = squareGridValidator;
    this.squareGridConverter = squareGridConverter;
    this.uiWidget = uiWidget;
    
    mouseDownHandlerRegistration = ((HasMouseDownHandlers)uiWidget).addMouseDownHandler(this);
    mouseUpHandlerRegistration = ((HasMouseUpHandlers)uiWidget).addMouseUpHandler(this);
    mouseMoveHandlerRegistration = ((HasMouseMoveHandlers)uiWidget).addMouseMoveHandler(this);
    mouseOutHandlerRegistration = ((HasMouseOutHandlers)uiWidget).addMouseOutHandler(this);  
  }

  @Override
  public void unbind() {

    if( mouseDownHandlerRegistration != null )
      mouseDownHandlerRegistration.removeHandler();
    if( mouseUpHandlerRegistration != null )
      mouseUpHandlerRegistration.removeHandler();
    if( mouseMoveHandlerRegistration != null )
      mouseMoveHandlerRegistration.removeHandler();
    if( mouseOutHandlerRegistration != null )
      mouseOutHandlerRegistration.removeHandler();
    
    mouseDownHandlerRegistration = null;
    mouseUpHandlerRegistration = null;
    mouseMoveHandlerRegistration = null;
    mouseOutHandlerRegistration = null;
  }
  

  @Override
  public void onMouseDown(MouseDownEvent event) {
    event.preventDefault(); // Prevents undesired element selection
    DOM.setCapture( uiWidget.getElement() );
    
    int x = event.getX();
    int y = event.getY();
    
    VertexHitInfo vertexHitInfo = vertexHit( x, y, vertexClickDistance );
    if( vertexHitInfo != null ) {
      fireEvent( new VertexMouseDownEvent(vertexHitInfo.getVertex()) );
      return;
    } 

    EdgeHitInfo edgeHitInfo = edgeHit( x, y, edgeClickDistance );
    if( edgeHitInfo != null ) {
      fireEvent( new EdgeMouseDownEvent(edgeHitInfo.isVertical(), edgeHitInfo.getEdge()) );
      return;
    } 
   
    Vec2i cell = cellHit( x, y );
    if( cell != null )
      fireEvent( new CellMouseDownEvent(cell) );
  }

  @Override
  public void onMouseUp(MouseUpEvent event) {
    event.preventDefault(); // Prevents undesired element selection
    DOM.releaseCapture( uiWidget.getElement() );
    
    int x = event.getX();
    int y = event.getY();
    
    VertexHitInfo vertexHitInfo = vertexHit( x, y, vertexClickDistance );
    if( vertexHitInfo != null ) {
      fireEvent( new VertexMouseUpEvent(vertexHitInfo.getVertex()) );
      return;
    } 

    EdgeHitInfo edgeHitInfo = edgeHit( x, y, edgeClickDistance );
    if( edgeHitInfo != null ) {
      fireEvent( new EdgeMouseUpEvent(edgeHitInfo.isVertical(), edgeHitInfo.getEdge()) );
      return;
    } 
   
    Vec2i cell = cellHit( x, y );
    if( cell != null )
      fireEvent( new CellMouseUpEvent(cell) );
  }

  @Override
  public void onMouseMove(MouseMoveEvent event) {
    event.preventDefault(); // Prevents undesired element selection
    mouseMovedTo(event.getX(), event.getY());
  }

  @Override
  public void onMouseOut(MouseOutEvent event) {
    event.preventDefault(); // Prevents undesired element selection
    mouseMovedTo(event.getX(), event.getY());
  }
  
  /**
   * Call whenever the mouse move, wether because of a 
   * {@link MouseMoveEvent} or a {@link MouseOutEvent}.
   * 
   * @param x The x pixel coordinate within {@link #uiWidget}.
   * @param y The y pixel coordinate within {@link #uiWidget}.
   */
  private void mouseMovedTo(int x, int y) {

    VertexHitInfo vertexHitInfo = vertexHit( x, y, vertexMoveDistance );
    if( vertexHitInfo != null ) {
      if( current != OVER_VERTEX ||
          !currentLoc.equals( vertexHitInfo.getVertex() ) ) {
        fireOutEvent();
        current = OVER_VERTEX;
        currentLoc = vertexHitInfo.getVertex();        
        fireEvent( new VertexMouseOverEvent(vertexHitInfo.getVertex()) );
      }
      fireEvent( new VertexMouseMoveEvent(vertexHitInfo.getVertex()) );
      return;
    } 

    EdgeHitInfo edgeHitInfo = edgeHit( x, y, edgeMoveDistance );
    if( edgeHitInfo != null ) {
      if( current != OVER_EDGE ||
          currentVertical != edgeHitInfo.isVertical() ||
          !currentLoc.equals( edgeHitInfo.getEdge() ) ) {
        fireOutEvent();
        current = OVER_EDGE;
        currentVertical = edgeHitInfo.isVertical();
        currentLoc = edgeHitInfo.getEdge();        
        fireEvent( new EdgeMouseOverEvent(edgeHitInfo.isVertical(), edgeHitInfo.getEdge()) );
      }
      fireEvent( new EdgeMouseMoveEvent(edgeHitInfo.isVertical(), edgeHitInfo.getEdge()) );
      return;
    } 
   
    Vec2i cell = cellHit( x, y );
    if( cell != null ) {
      if( current != OVER_CELL ||
          !currentLoc.equals( cell ) ) {
        fireOutEvent();
        current = OVER_CELL;
        currentLoc = cell;        
        fireEvent( new CellMouseOverEvent(cell) );
      }
      fireEvent( new CellMouseMoveEvent(cell) );
      return;
    } 
    
    fireOutEvent();
    current = OVER_NOTHING;
    currentLoc = null;
  }

  /**
   * Fires the event indicating the the currently over
   * element has been left.
   */
  private void fireOutEvent() {
    if( current == OVER_NOTHING )
      return;
    else if( current == OVER_CELL )
      fireEvent( new CellMouseOutEvent(currentLoc) );
    else if( current == OVER_EDGE )
      fireEvent( new EdgeMouseOutEvent(currentVertical, currentLoc) );
    else if( current == OVER_VERTEX )
      fireEvent( new VertexMouseOutEvent(currentLoc) );
  }

  @Override
  public void setVertexClickDistance(int vertexClickDistance) {
    this.vertexClickDistance = vertexClickDistance;
  }
  
  @Override
  public void setVertexMoveDistance(int vertexMoveDistance) {
    this.vertexMoveDistance = vertexMoveDistance;
  }

  @Override
  public void setVertexDistance(int vertexDistance) {
    setVertexClickDistance(vertexDistance);
    setVertexMoveDistance(vertexDistance);
  }

  @Override
  public void setEdgeClickDistance(int edgeClickDistance) {
    this.edgeClickDistance = edgeClickDistance;
  }

  @Override
  public void setEdgeMoveDistance(int edgeMoveDistance) {
    this.edgeMoveDistance = edgeMoveDistance;
  }
  
  @Override
  public void setEdgeDistance(int edgeDistance) {
    setEdgeClickDistance(edgeDistance);
    setEdgeMoveDistance(edgeDistance);
  }
  
  @Override
  public HandlerRegistration addCellMouseDownHandler(CellMouseDownHandler handler) {
    return ensureHandlers().addHandler(CellMouseDownEvent.getType(), handler);
  }
    
  @Override
  public HandlerRegistration addCellMouseUpHandler(CellMouseUpHandler handler){
    return ensureHandlers().addHandler(CellMouseUpEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addCellMouseOverHandler(CellMouseOverHandler handler){
    return ensureHandlers().addHandler(CellMouseOverEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addCellMouseOutHandler(CellMouseOutHandler handler){
    return ensureHandlers().addHandler(CellMouseOutEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addCellMouseMoveHandler(CellMouseMoveHandler handler){
    return ensureHandlers().addHandler(CellMouseMoveEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addEdgeMouseDownHandler(EdgeMouseDownHandler handler){
    return ensureHandlers().addHandler(EdgeMouseDownEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addEdgeMouseUpHandler(EdgeMouseUpHandler handler){
    return ensureHandlers().addHandler(EdgeMouseUpEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addEdgeMouseOverHandler(EdgeMouseOverHandler handler){
    return ensureHandlers().addHandler(EdgeMouseOverEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addEdgeMouseOutHandler(EdgeMouseOutHandler handler){
    return ensureHandlers().addHandler(EdgeMouseOutEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addEdgeMouseMoveHandler(EdgeMouseMoveHandler handler){
    return ensureHandlers().addHandler(EdgeMouseMoveEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addVertexMouseDownHandler(VertexMouseDownHandler handler){
    return ensureHandlers().addHandler(VertexMouseDownEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addVertexMouseUpHandler(VertexMouseUpHandler handler){
    return ensureHandlers().addHandler(VertexMouseUpEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addVertexMouseOverHandler(VertexMouseOverHandler handler){
    return ensureHandlers().addHandler(VertexMouseOverEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addVertexMouseOutHandler(VertexMouseOutHandler handler){
    return ensureHandlers().addHandler(VertexMouseOutEvent.getType(), handler);
  }
   
  @Override
  public HandlerRegistration addVertexMouseMoveHandler(VertexMouseMoveHandler handler){
    return ensureHandlers().addHandler(VertexMouseMoveEvent.getType(), handler);
  }
  
  /**
   * Fires an event that could be handled by the handlers registered
   * towards this object.
   * 
   * @param event The {@link GwtEvent} to fire.
   */
  private void fireEvent(GwtEvent<?> event) {
    if (handlerManager != null) {
      handlerManager.fireEvent(event);
    }
  }
  
  /**
   * Ensures the existence of the handler manager.
   * 
   * @return the handler manager
   * */
  HandlerManager ensureHandlers() {
    return handlerManager == null ? handlerManager = new HandlerManager(this)
        : handlerManager;
  }
  

  /**
   * Checks if the passed coordinate hits a vertex within the specified
   * threshold distance.
   * 
   * @param x The x coordinate to test, in pixels.
   * @param y The y coordinate to test, in pixels.
   * @param distance The threshold distance.
   * @return The {@link VertexHitInfo} of the hit vertex, or {@code null} if no vertex is hit.
   */
  private VertexHitInfo vertexHit(int x, int y, int distance) {
    if( distance < 0 || squareGridConverter == null || squareGridValidator == null) 
      return null;        
    VertexHitInfo vertexHitInfo = squareGridConverter.pixelToVertex(x, y);
    if( vertexHitInfo != null &&
        squareGridValidator.isValidVertex( vertexHitInfo.getVertex() ) && 
        vertexHitInfo.getDist().max() <= distance )
      return vertexHitInfo;
    return null;
  }
  
  /**
   * Checks if the passed coordinate hits an edge within the specified
   * threshold distance.
   * 
   * @param x The x coordinate to test, in pixels.
   * @param y The y coordinate to test, in pixels.
   * @param distance The threshold distance.
   * @return The {@link EdgeHitInfo} of the hit edge, or {@code null} if no edge is hit.
   */
  private EdgeHitInfo edgeHit(int x, int y, int distance) {
    if( distance < 0 || squareGridConverter == null || squareGridValidator == null ) 
      return null;
    EdgeHitInfo edgeHitInfo = squareGridConverter.pixelToEdge(x, y);
    if( edgeHitInfo != null &&
        edgeHitInfo.isVertical() && 
        squareGridValidator.isValidVerticalEdge( edgeHitInfo.getEdge() ) && 
        edgeHitInfo.getDist() <= distance ) 
      return edgeHitInfo;
    if( edgeHitInfo != null &&
        !edgeHitInfo.isVertical() && 
        squareGridValidator.isValidHorizontalEdge( edgeHitInfo.getEdge() ) && 
        edgeHitInfo.getDist() <= 4 )
      return edgeHitInfo;
    return null;
  }
  
  /**
   * Checks if the passed coordinate hits a cell.
   * 
   * @param x The x coordinate to test, in pixels.
   * @param y The y coordinate to test, in pixels.
   * @return The {@link Vec2i} containing the coordinates of the hit cell, or {@code null} if no cell is hit.
   */
  private Vec2i cellHit(int x, int y) {
    if( squareGridConverter == null || squareGridValidator == null )
      return null;
    Vec2i cell = squareGridConverter.pixelToCell(x, y);
    if( squareGridValidator.isValidCell( cell ) )
      return cell;
    return null;
  }
  
}
