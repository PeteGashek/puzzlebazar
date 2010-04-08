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
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class hooks up to both a {@link SquareGridLayoutPanel} and a 
 * {@link Widget}. The latter is used for ui interaction and must implement
 * a number of mouse handler interfaces ({@link HasMouseDownHandlers},
 * {@link HasMouseUpHandlers}, {@link HasMouseMouveHandlers},
 * {@link HasMouseOutHandlers}). In turn, this {@link SquareGridManipulator}
 * implements a number of square-grid-specific handlers.
 * 
 * @author Philippe Beaudoin
 */
public interface SquareGridManipulator {
  
  /**
   * The factory class to produce {@link SquareGridManipulator} objects.
   * 
   * @author Philippe Beaudoin
   */
  public interface Factory {

    /**
     * Creates a {@link SquareGridManipulator} attached to a panel 
     * and a UI widget.
     * 
     * @param gridPanel The {@link SquareGridLayoutPanel} on which to add a manipulator.
     * @param uiWidget The {@link Widget} receiving the ui input. Must implement
     *   the following interfaces: {@link HasMouseDownHandlers},
     *   {@link HasMouseUpHandlers}, {@link HasMouseMouveHandlers},
     *   {@link HasMouseOutHandlers}.
     */  
    public SquareGridManipulator create(
        SquareGridLayoutPanel gridPanel,
        Widget uiWidget );
  }
  
  /**
   * Call this method when you're done using the manipulator, to ensure all 
   * UI handlers are unbound.
   */
  public void unbind();

  /**
   * Sets the distance around a vertex where a mouse down or mouse
   * up will be reported. This defines a square of sensitivity around the vertex.
   * 
   * @param vertexClickDistance The distance, in pixels. 
   *        Passing -1 will turn off vertex mouse down, mouse up events.
   */
  public void setVertexClickDistance(int vertexClickDistance);

  /**
   * Sets the distance around a vertex where a mouse move, mouse over and mouse out
   * will be reported. This defines a square of sensitivity around the vertex.
   * 
   * @param vertexMoveDistance The distance, in pixels.
   *        Passing -1 will turn off vertex mouse move, mouse over and mouse out events.
   */
  public void setVertexMoveDistance(int vertexMoveDistance);

  /**
   * Sets the distance around a vertex where a mouse down, mouse up, mouse move, mouse over and mouse out
   * will be reported. This defines a square of sensitivity around the vertex.
   * 
   * @param vertexDistance The distance, in pixels.
   *        Passing -1 will turn off vertex mouse down, mouse up, mouse move, mouse over and mouse out events.
   */
  public void setVertexDistance(int vertexDistance);

  /**
   * Sets the distance around an edge where a mouse down or mouse
   * up will be reported. This defines a region of sensitivity around the edge.
   * 
   * @param edgeClickDistance The distance, in pixels. 
   *        Passing -1 will turn off edge mouse down, mouse up events.
   */
  public void setEdgeClickDistance(int edgeClickDistance);

  /**
   * Sets the distance around an edge where a mouse move, mouse over and mouse out
   * will be reported. This defines a region of sensitivity around the edge.
   * 
   * @param edgeMoveDistance The distance, in pixels.
   *        Passing -1 will turn off edge mouse move, mouse over and mouse out events.
   */
  public void setEdgeMoveDistance(int edgeMoveDistance);

  /**
   * Sets the distance around an edge where a mouse down, mouse up, mouse move, mouse over and mouse out
   * will be reported. This defines a region of sensitivity around the edge.
   * 
   * @param edgeDistance The distance, in pixels.
   *        Passing -1 will turn off edge mouse down, mouse up, mouse move, mouse over and mouse out events.
   */
  public void setEdgeDistance(int edgeDistance);

  /**
   * Adds an handler for cell mouse down event.
   * 
   * @param handler The {@link CellMouseDownHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addCellMouseDownHandler(
      CellMouseDownHandler handler);

  /**
   * Adds an handler for cell mouse up event.
   * 
   * @param handler The {@link CellMouseUpHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addCellMouseUpHandler(CellMouseUpHandler handler);

  /**
   * Adds an handler for cell mouse over event.
   * 
   * @param handler The {@link CellMouseOverHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addCellMouseOverHandler(
      CellMouseOverHandler handler);

  /**
   * Adds an handler for cell mouse out event.
   * 
   * @param handler The {@link CellMouseOutHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addCellMouseOutHandler(CellMouseOutHandler handler);

  /**
   * Adds an handler for cell mouse move event.
   * 
   * @param handler The {@link CellMouseMoveHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addCellMouseMoveHandler(
      CellMouseMoveHandler handler);

  /**
   * Adds an handler for edge mouse down event.
   * For this to work, you must set a non-negative distance with
   * {@link #setEdgeClickDistance(int)}.
   * 
   * @param handler The {@link EdgeMouseUpHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addEdgeMouseDownHandler(
      EdgeMouseDownHandler handler);

  /**
   * Adds an handler for edge mouse up event.
   * For this to work, you must set a non-negative distance with
   * {@link #setEdgeClickDistance(int)}.
   * 
   * @param handler The {@link EdgeMouseUpHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addEdgeMouseUpHandler(EdgeMouseUpHandler handler);

  /**
   * Adds an handler for edge mouse over event.
   * For this to work, you must set a non-negative distance with
   * {@link #setEdgeMoveDistance(int)}.
   * 
   * @param handler The {@link EdgeMouseOverHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addEdgeMouseOverHandler(
      EdgeMouseOverHandler handler);

  /**
   * Adds an handler for edge mouse out event.
   * For this to work, you must set a non-negative distance with
   * {@link #setEdgeMoveDistance(int)}.
   * 
   * @param handler The {@link EdgeMouseOutHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addEdgeMouseOutHandler(EdgeMouseOutHandler handler);

  /**
   * Adds an handler for edge mouse move event.
   * For this to work, you must set a non-negative distance with
   * {@link #setEdgeMoveDistance(int)}.
   * 
   * @param handler The {@link EdgeMouseMoveHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addEdgeMouseMoveHandler(
      EdgeMouseMoveHandler handler);

  /**
   * Adds an handler for vertex mouse down event.
   * For this to work, you must set a non-negative distance with
   * {@link #setVertexClickDistance(int)}.
   * 
   * @param handler The {@link VertexMouseDownHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addVertexMouseDownHandler(
      VertexMouseDownHandler handler);

  /**
   * Adds an handler for vertex mouse up event.
   * For this to work, you must set a non-negative distance with
   * {@link #setVertexClickDistance(int)}.
   * 
   * @param handler The {@link VertexMouseUpHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addVertexMouseUpHandler(
      VertexMouseUpHandler handler);

  /**
   * Adds an handler for vertex mouse over event.
   * For this to work, you must set a non-negative distance with
   * {@link #setVertexMoveDistance(int)}.
   * 
   * @param handler The {@link VertexMouseOverHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addVertexMouseOverHandler(
      VertexMouseOverHandler handler);

  /**
   * Adds an handler for vertex mouse out event.
   * For this to work, you must set a non-negative distance with
   * {@link #setVertexMoveDistance(int)}.
   * 
   * @param handler The {@link VertexMouseOutHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addVertexMouseOutHandler(
      VertexMouseOutHandler handler);

  /**
   * Adds an handler for vertex move down event.
   * For this to work, you must set a non-negative distance with
   * {@link #setVertexMoveDistance(int)}.
   * 
   * @param handler The {@link VertexMouseMoveHandler} to attach to this event.
   * @return The {@link HandlerRegistration}. Use this to remove the handler when desired.
   */
  public HandlerRegistration addVertexMouseMoveHandler(
      VertexMouseMoveHandler handler);

}