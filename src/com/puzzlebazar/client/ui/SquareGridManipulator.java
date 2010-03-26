package com.puzzlebazar.client.ui;

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
import com.google.inject.Inject;
import com.puzzlebazar.client.util.SquareGridConverter;
import com.puzzlebazar.client.util.SquareGridConverter.EdgeInfo;
import com.puzzlebazar.client.util.SquareGridConverter.VertexInfo;
import com.puzzlebazar.shared.util.SquareGridValidator;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * This class hooks up to both a {@link SquareGridLayoutPanel} and a 
 * {@link Widget}. The latter is used for ui interaction and must implement
 * a number of mouse handler interfaces ({@link HasMouseDownHandlers},
 * {@link HasMouseUpHandlers}, {@link HasMouseMouveHandlers},
 * {@link HasMouseOutHandlers}). In turn, this {@link SquareGridManipulator}
 * implements a number of square-grid-specific handlers:
 * {@link #addCellMouseDownHandler(CellMouseDownHandler)},
 * {@link #addCellMouseUpHandler(CellMouseUpHandler)},
 * {@link #addCellMouseOverHandler(CellMouseOverHandler)},
 * {@link #addCellMouseOutHandler(CellMouseOutHandler)},
 * {@link #addCellMouseMoveHandler(CellMouseMoveHandler)},
 * {@link #addEdgeMouseDownHandler(EdgeMouseDownHandler)},
 * {@link #addEdgeMouseUpHandler(EdgeMouseUpHandler)},
 * {@link #addEdgeMouseOverHandler(EdgeMouseOverHandler)},
 * {@link #addEdgeMouseOutHandler(EdgeMouseOutHandler)},
 * {@link #addEdgeMouseMoveHandler(EdgeMouseMoveHandler)},
 * {@link #addVertexMouseDownHandler(VertexMouseDownHandler)},
 * {@link #addVertexMouseUpHandler(VertexMouseUpHandler)},
 * {@link #addVertexMouseOverHandler(VertexMouseOverHandler)},
 * {@link #addVertexMouseOutHandler(VertexMouseOutHandler)},
 * {@link #addVertexMouseMoveHandler(VertexMouseMoveHandler)}.
 * 
 * @author Philippe Beaudoin
 */
public class SquareGridManipulator 
implements MouseDownHandler, MouseUpHandler, MouseMoveHandler, 
MouseOutHandler {

  private final SquareGridValidator squareGridValidator;
  private final SquareGridConverter squareGridConverter;

  private HandlerManager handlerManager = null;
  
  private SquareGridLayoutPanel gridPanel = null;
  private Widget uiWidget = null;

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
    
  @Inject
  public SquareGridManipulator(
      SquareGridValidator squareGridValidator,
      SquareGridConverter squareGridConverter ) {
    this.squareGridValidator = squareGridValidator;
    this.squareGridConverter = squareGridConverter;
  }
  
  /**
   * Binds this manipulator to its (weak) aggregated participants.
   * 
   * @param gridPanel The grid panel on which to add a manipulator.
   * @param uiWidget The widget receiving the ui input. Must implement
   *   the following interfaces: {@link HasMouseDownHandlers},
   *   {@link HasMouseUpHandlers}, {@link HasMouseMouveHandlers},
   *   {@link HasMouseOutHandlers}.
   */
  public void bind( 
      SquareGridLayoutPanel gridPanel,
      Widget uiWidget ) {
    assert this.gridPanel == null && this.uiWidget == null : 
      "Cannot bind SquareGridManipulator twice.";

    this.gridPanel = gridPanel;
    this.uiWidget = uiWidget;
    
    squareGridValidator.bind( gridPanel );
    squareGridConverter.bind( gridPanel, uiWidget );
    
    mouseDownHandlerRegistration = ((HasMouseDownHandlers)uiWidget).addMouseDownHandler(this);
    mouseUpHandlerRegistration = ((HasMouseUpHandlers)uiWidget).addMouseUpHandler(this);
    mouseMoveHandlerRegistration = ((HasMouseMoveHandlers)uiWidget).addMouseMoveHandler(this);
    mouseOutHandlerRegistration = ((HasMouseOutHandlers)uiWidget).addMouseOutHandler(this);
  }

  public void unbind() {
    assert this.gridPanel != null && this.uiWidget != null : 
      "Class SquareGridManipulator must be bound before it is unbound.";

    mouseDownHandlerRegistration.removeHandler();
    mouseUpHandlerRegistration.removeHandler();
    mouseMoveHandlerRegistration.removeHandler();
    mouseOutHandlerRegistration.removeHandler();
    
    squareGridValidator.unbind();
    squareGridConverter.unbind();
    
    this.gridPanel = null;
    this.uiWidget = null;
  }
  

  @Override
  public void onMouseDown(MouseDownEvent event) {
    event.preventDefault(); // Prevents undesired element selection
    DOM.setCapture( uiWidget.getElement() );
    
    int x = event.getX();
    int y = event.getY();
    
    VertexInfo vertexInfo = vertexHit( x, y, vertexClickDistance );
    if( vertexInfo != null ) {
      fireEvent( new VertexMouseDownEvent(vertexInfo.vertex) );
      return;
    } 

    EdgeInfo edgeInfo = edgeHit( x, y, edgeClickDistance );
    if( edgeInfo != null ) {
      fireEvent( new EdgeMouseDownEvent(edgeInfo.isVertical, edgeInfo.edge) );
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
    
    VertexInfo vertexInfo = vertexHit( x, y, vertexClickDistance );
    if( vertexInfo != null ) {
      fireEvent( new VertexMouseUpEvent(vertexInfo.vertex) );
      return;
    } 

    EdgeInfo edgeInfo = edgeHit( x, y, edgeClickDistance );
    if( edgeInfo != null ) {
      fireEvent( new EdgeMouseUpEvent(edgeInfo.isVertical, edgeInfo.edge) );
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

    VertexInfo vertexInfo = vertexHit( x, y, vertexMoveDistance );
    if( vertexInfo != null ) {
      if( current != OVER_VERTEX ||
          !currentLoc.equals( vertexInfo.vertex ) ) {
        fireOutEvent();
        current = OVER_VERTEX;
        currentLoc = vertexInfo.vertex;        
        fireEvent( new VertexMouseOverEvent(vertexInfo.vertex) );
      }
      fireEvent( new VertexMouseMoveEvent(vertexInfo.vertex) );
      return;
    } 

    EdgeInfo edgeInfo = edgeHit( x, y, edgeMoveDistance );
    if( edgeInfo != null ) {
      if( current != OVER_EDGE ||
          currentVertical != edgeInfo.isVertical ||
          !currentLoc.equals( edgeInfo.edge ) ) {
        fireOutEvent();
        current = OVER_EDGE;
        currentVertical = edgeInfo.isVertical;
        currentLoc = edgeInfo.edge;        
        fireEvent( new EdgeMouseOverEvent(edgeInfo.isVertical, edgeInfo.edge) );
      }
      fireEvent( new EdgeMouseMoveEvent(edgeInfo.isVertical, edgeInfo.edge) );
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

  /**
   * Sets the distance around a vertex where a mouse down or mouse
   * up will be reported. This defines a square of sensitivity around the vertex.
   * 
   * @param vertexClickDistance The distance, in pixels. 
   *        Passing -1 will turn off vertex mouse down, mouse up events.
   */
  public void setVertexClickDistance(int vertexClickDistance) {
    this.vertexClickDistance = vertexClickDistance;
  }
  
  /**
   * Sets the distance around a vertex where a mouse move, mouse over and mouse out
   * will be reported. This defines a square of sensitivity around the vertex.
   * 
   * @param vertexMoveDistance The distance, in pixels.
   *        Passing -1 will turn off vertex mouse move, mouse over and mouse out events.
   */
  public void setVertexMoveDistance(int vertexMoveDistance) {
    this.vertexMoveDistance = vertexMoveDistance;
  }

  /**
   * Sets the distance around a vertex where a mouse down, mouse up, mouse move, mouse over and mouse out
   * will be reported. This defines a square of sensitivity around the vertex.
   * 
   * @param vertexDistance The distance, in pixels.
   *        Passing -1 will turn off vertex mouse down, mouse up, mouse move, mouse over and mouse out events.
   */
  public void setVertexDistance(int vertexDistance) {
    setVertexClickDistance(vertexDistance);
    setVertexMoveDistance(vertexDistance);
  }

  /**
   * Sets the distance around an edge where a mouse down or mouse
   * up will be reported. This defines a region of sensitivity around the edge.
   * 
   * @param edgeClickDistance The distance, in pixels. 
   *        Passing -1 will turn off edge mouse down, mouse up events.
   */
  public void setEdgeClickDistance(int edgeClickDistance) {
    this.edgeClickDistance = edgeClickDistance;
  }

  /**
   * Sets the distance around an edge where a mouse move, mouse over and mouse out
   * will be reported. This defines a region of sensitivity around the edge.
   * 
   * @param edgeMoveDistance The distance, in pixels.
   *        Passing -1 will turn off edge mouse move, mouse over and mouse out events.
   */
  public void setEdgeMoveDistance(int edgeMoveDistance) {
    this.edgeMoveDistance = edgeMoveDistance;
  }
  
  /**
   * Sets the distance around an edge where a mouse down, mouse up, mouse move, mouse over and mouse out
   * will be reported. This defines a region of sensitivity around the edge.
   * 
   * @param edgeDistance The distance, in pixels.
   *        Passing -1 will turn off edge mouse down, mouse up, mouse move, mouse over and mouse out events.
   */
  public void setEdgeDistance(int edgeDistance) {
    setEdgeClickDistance(edgeDistance);
    setEdgeMoveDistance(edgeDistance);
  }
  
  /**
   * Adds an handler for cell mouse down event.
   * 
   * @param handler The {@link CellMouseDownHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addCellMouseDownHandler(CellMouseDownHandler handler) {
    return ensureHandlers().addHandler(CellMouseDownEvent.getType(), handler);
  }
    
  /**
   * Adds an handler for cell mouse up event.
   * 
   * @param handler The {@link CellMouseUpHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addCellMouseUpHandler(CellMouseUpHandler handler){
    return ensureHandlers().addHandler(CellMouseUpEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for cell mouse over event.
   * 
   * @param handler The {@link CellMouseOverHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addCellMouseOverHandler(CellMouseOverHandler handler){
    return ensureHandlers().addHandler(CellMouseOverEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for cell mouse out event.
   * 
   * @param handler The {@link CellMouseOutHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addCellMouseOutHandler(CellMouseOutHandler handler){
    return ensureHandlers().addHandler(CellMouseOutEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for cell mouse move event.
   * 
   * @param handler The {@link CellMouseMoveHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addCellMouseMoveHandler(CellMouseMoveHandler handler){
    return ensureHandlers().addHandler(CellMouseMoveEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for edge mouse down event.
   * For this to work, you must set a non-negative distance with
   * {@link #setEdgeClickDistance(int)}.
   * 
   * @param handler The {@link EdgeMouseUpHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addEdgeMouseDownHandler(EdgeMouseDownHandler handler){
    return ensureHandlers().addHandler(EdgeMouseDownEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for edge mouse up event.
   * For this to work, you must set a non-negative distance with
   * {@link #setEdgeClickDistance(int)}.
   * 
   * @param handler The {@link EdgeMouseUpHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addEdgeMouseUpHandler(EdgeMouseUpHandler handler){
    return ensureHandlers().addHandler(EdgeMouseUpEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for edge mouse over event.
   * For this to work, you must set a non-negative distance with
   * {@link #setEdgeMoveDistance(int)}.
   * 
   * @param handler The {@link EdgeMouseOverHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addEdgeMouseOverHandler(EdgeMouseOverHandler handler){
    return ensureHandlers().addHandler(EdgeMouseOverEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for edge mouse out event.
   * For this to work, you must set a non-negative distance with
   * {@link #setEdgeMoveDistance(int)}.
   * 
   * @param handler The {@link EdgeMouseOutHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addEdgeMouseOutHandler(EdgeMouseOutHandler handler){
    return ensureHandlers().addHandler(EdgeMouseOutEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for edge mouse move event.
   * For this to work, you must set a non-negative distance with
   * {@link #setEdgeMoveDistance(int)}.
   * 
   * @param handler The {@link EdgeMouseMoveHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addEdgeMouseMoveHandler(EdgeMouseMoveHandler handler){
    return ensureHandlers().addHandler(EdgeMouseMoveEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for vertex mouse down event.
   * For this to work, you must set a non-negative distance with
   * {@link #setVertexClickDistance(int)}.
   * 
   * @param handler The {@link VertexMouseDownHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addVertexMouseDownHandler(VertexMouseDownHandler handler){
    return ensureHandlers().addHandler(VertexMouseDownEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for vertex mouse up event.
   * For this to work, you must set a non-negative distance with
   * {@link #setVertexClickDistance(int)}.
   * 
   * @param handler The {@link VertexMouseUpHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addVertexMouseUpHandler(VertexMouseUpHandler handler){
    return ensureHandlers().addHandler(VertexMouseUpEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for vertex mouse over event.
   * For this to work, you must set a non-negative distance with
   * {@link #setVertexMoveDistance(int)}.
   * 
   * @param handler The {@link VertexMouseOverHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addVertexMouseOverHandler(VertexMouseOverHandler handler){
    return ensureHandlers().addHandler(VertexMouseOverEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for vertex mouse out event.
   * For this to work, you must set a non-negative distance with
   * {@link #setVertexMoveDistance(int)}.
   * 
   * @param handler The {@link VertexMouseOutHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addVertexMouseOutHandler(VertexMouseOutHandler handler){
    return ensureHandlers().addHandler(VertexMouseOutEvent.getType(), handler);
  }
   
  /**
   * Adds an handler for vertex move down event.
   * For this to work, you must set a non-negative distance with
   * {@link #setVertexMoveDistance(int)}.
   * 
   * @param handler The {@link VertexMouseMoveHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
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
   * @return The {@link VertexInfo} of the hit vertex, or {@code null} if no vertex is hit.
   */
  private VertexInfo vertexHit(int x, int y, int distance) {
    if( distance < 0 || squareGridConverter == null || squareGridValidator == null) 
      return null;        
    VertexInfo vertexInfo = squareGridConverter.pixelToVertex(x, y);
    if( squareGridValidator.isValidVertex( vertexInfo.vertex ) && vertexInfo.dist.max() <= distance )
      return vertexInfo;
    return null;
  }
  
  /**
   * Checks if the passed coordinate hits an edge within the specified
   * threshold distance.
   * 
   * @param x The x coordinate to test, in pixels.
   * @param y The y coordinate to test, in pixels.
   * @param distance The threshold distance.
   * @return The {@link EdgeInfo} of the hit edge, or {@code null} if no edge is hit.
   */
  private EdgeInfo edgeHit(int x, int y, int distance) {
    if( distance < 0 || squareGridConverter == null || squareGridValidator == null ) 
      return null;
    EdgeInfo edgeInfo = squareGridConverter.pixelToEdge(x, y);
    if( edgeInfo.isVertical && 
        squareGridValidator.isValidVerticalEdge( edgeInfo.edge ) && 
        edgeInfo.dist <= distance ) 
      return edgeInfo;
    if( !edgeInfo.isVertical && 
        squareGridValidator.isValidHorizontalEdge( edgeInfo.edge ) && 
        edgeInfo.dist <= 4 )
      return edgeInfo;
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
