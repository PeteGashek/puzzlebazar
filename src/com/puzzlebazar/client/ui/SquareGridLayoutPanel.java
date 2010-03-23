package com.puzzlebazar.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.util.Recti;
import com.puzzlebazar.client.util.SquareGrid;
import com.puzzlebazar.client.util.Vec2i;

/**
 * <b>Important!</b> This layout panel must be added inside a {@link com.google.gwt.user.client.ui.LayoutPanel}.
 * <p />
 * This widget represents a square grid and is often useful to draw puzzles that use such grids.
 * It holds the square grid and the desired border around it.
 * 
 * @author Philippe Beaudoin
 */
public class SquareGridLayoutPanel extends AspectRatioLayoutPanel implements SquareGrid {

  private static class CellLayoutPanel extends OverflowLayoutPanel {

    /**
     * Adds a widget that should occupy the top-left vertex.
     * 
     * @param widget The widget that should occupy the top-left vertex.
     * @param thickness Its thickness, in pixels.
     */
    private void addVertexWidget( Widget widget, int thickness ) {
      add( widget );
      int halfThickness = thickness/2;
      setWidgetLeftWidth(widget, -halfThickness, Unit.PX, thickness, Unit.PX);
      setWidgetTopHeight(widget, -halfThickness, Unit.PX, thickness, Unit.PX);
    }

    /**
     * Adds a widget that should occupy the left edge.
     * 
     * @param widget The widget that should occupy the left edge.
     * @param thickness Its thickness, in pixels.
     */
    private void addLeftEdgeWidget( Widget widget, int thickness ) {
      add( widget );
      int halfThickness = thickness/2;
      int thicknessRemainder = thickness - halfThickness;
      setWidgetLeftWidth(widget, -halfThickness, Unit.PX, thickness, Unit.PX);
      setWidgetTopBottom(widget, -halfThickness, Unit.PX, -thicknessRemainder+1, Unit.PX);
    }

    /**
     * Adds a widget that should occupy the top edge.
     * 
     * @param widget The widget that should occupy the top edge.
     * @param thickness Its thickness, in pixels.
     */
    private void addTopEdgeWidget( Widget widget, int thickness ) {
      add( widget );
      int halfThickness = thickness/2;
      int thicknessRemainder = thickness - halfThickness;
      setWidgetTopHeight(widget, -halfThickness, Unit.PX, thickness, Unit.PX);
      setWidgetLeftRight(widget, -halfThickness, Unit.PX, -thicknessRemainder+1, Unit.PX);
    }

    /**
     * Adds a widget that should occupy the cell.
     * 
     * @param widget The widget that should occupy the cell.
     */
    private void addCellWidget( Widget widget ) {
      add( widget );
    }

  }

  private final Resources resources;

  private int border = 0;
  public int width = 0;
  public int height = 0;

  // This container holds the square grid elements themselves, such as
  // grid cells or grid edges. 
  private OverflowLayoutPanel squareGridContainer;

  private CellLayoutPanel cells[][];

  @Inject
  public SquareGridLayoutPanel( Resources resources ) {
    super();

    this.resources = resources;

    squareGridContainer = GWT.create( OverflowLayoutPanel.class );
    add( squareGridContainer ); 
    setBorder( border );
  }
  
  /**
   * Changes the desired border between the edges of the square grid
   * and that of its container. This border will allow the square grid
   * to draw elements slightly outside itself. It's useful for drawing
   * thicker outside lines, for example. 
   * 
   * @param border The desired border size, in pixels.
   */
  public void setBorder( int border ) {
    // The 1 extra pixel on the right and bottom is so that elements positioned at 100%
    // within the puzzleContainer fall inside the mainContainer.
    setWidgetLeftRight( squareGridContainer, border, Unit.PX, border+1, Unit.PX );
    setWidgetTopBottom( squareGridContainer, border, Unit.PX, border+1, Unit.PX );
    super.setBorder( border );
  }
  
  /**
   * Sets the size of the square grid. All the grid content will be lost.
   * 
   * @param width The desired number or grid cells along the horizontal direction. 
   * @param height The desired number or grid cells along the vertical direction.
   */
  public void setSize( int width, int height ) {
    this.width = width;
    this.height = height;
    setAspectRatio( width/(float)height );
    createCellPanels();
  }

  @Override
  public int getWidth() {
    return width;    
  }
  
  @Override
  public int getHeight() {
    return height;
  }
  
  /**
   * Adds a the widget to draw at a specific vertex.
   * (See {@link Recti} for details on cells and vertices.) 
   * 
   * @param loc The location of this vertex (a {@link Vec2i}). 
   * @param widget The {@link Widget} to set.
   * @param thickness The desired thickness for this edge (in pixels).
   */
  public void addVertexWidget( Vec2i loc, Widget widget, int thickness ) {
    assert 0 <= loc.x && loc.x <= width : "The x coordinate must be a valid vertex coordinate."; 
    assert 0 <= loc.y && loc.y <= height : "The y coordinate must be a valid vertex coordinate."; 
    cells[loc.x][loc.y].addVertexWidget(widget, thickness);
  }

  /**
   * Adds a the widget to draw at a specific vertical edge.
   * 
   * @param loc The location of this vertical edge. A {@link Vec2i} where the 
   *            x coordinate is a vertex coordinate and the y coordinate is a cell coordinate.
   *            (See {@link Recti} for details on cells and vertices.) 
   * @param widget The {@link Widget} to set.
   * @param thickness The desired thickness for this edge (in pixels).
   */
  public void addVerticalEdgeWidget( Vec2i loc, Widget widget, int thickness ) {
    assert 0 <= loc.x && loc.x <= width : "The x coordinate must be a valid vertex coordinate."; 
    assert 0 <= loc.y && loc.y < height : "The y coordinate must be a valid cell coordinate."; 
    cells[loc.x][loc.y].addLeftEdgeWidget(widget, thickness);
  }

  /**
   * Adds a widget to draw at a specific horizontal edge.
   * 
   * @param loc The location of this horizontal edge. A {@link Vec2i} where the 
   *            x coordinate is a cell coordinate and the y coordinate is a vertex coordinate.
   *            (See {@link Recti} for details on cells and vertices.) 
   * @param widget The {@link Widget} to set.
   * @param thickness The desired thickness for this edge (in pixels).
   */
  public void addHorizontalEdgeWidget( Vec2i loc, Widget widget, int thickness ) {
    assert 0 <= loc.x && loc.x < width : "The x coordinate must be a valid cell coordinate."; 
    assert 0 <= loc.y && loc.y <= height : "The y coordinate must be a valid vertex coordinate."; 
    cells[loc.x][loc.y].addTopEdgeWidget(widget, thickness);
  }

  /**
   * Adds a the widget to draw at a specific cell.
   * (See {@link Recti} for details on cells and vertices.) 
   * 
   * @param loc The location of this cell (a {@link Vec2i}). 
   * @param widget The {@link Widget} to set.
   */
  public void addCellWidget( Vec2i loc, Widget widget ) {
    assert 0 <= loc.x && loc.x < width : "The x coordinate must be a valid cell coordinate."; 
    assert 0 <= loc.y && loc.y < height : "The y coordinate must be a valid cell coordinate."; 
    cells[loc.x][loc.y].addCellWidget(widget);
  }

  /**
   * Creates a standard vertex widget and adds it at a specific vertex.
   * (See {@link Recti} for details on cells and vertices.) 
   * 
   * @param loc The location of this vertex (a {@link Vec2i}). 
   * @param thickness The desired thickness for this edge (in pixels).
   * @param styleNames A list of string containing all the desired style names. (The default edge style is added automatically.)
   * @return The newly created widget.
   */
  public Widget createVertex( Vec2i loc, int thickness, String... styleNames ) {
    Widget widget = GWT.create( FlowPanel.class );
    widget.addStyleName( resources.style().vertex() );
    for( String style : styleNames )
      widget.addStyleName( style );
    addVertexWidget( loc, widget, thickness );
    return widget;
  }

  /**
   * Creates a standard edge widget and adds it to the square
   * at a specific vertical edge.
   * 
   * @param loc The location of this horizontal edge. A {@link Vec2i} where the 
   *            x coordinate is a cell coordinate and the y coordinate is a vertex coordinate.
   *            (See {@link Recti} for details on cells and vertices.) 
   * @param thickness The desired thickness for this edge (in pixels).
   * @param styleNames A list of string containing all the desired style names. (The default edge style is added automatically.)
   * @return The newly created widget.
   */
  public Widget createHorizontalEdge( Vec2i loc, int thickness, String... styleNames ) {
    Widget widget = GWT.create( FlowPanel.class );
    widget.addStyleName( resources.style().edge() );
    for( String style : styleNames )
      widget.addStyleName( style );
    addHorizontalEdgeWidget( loc, widget, thickness );
    return widget;
  }


  /**
   * Creates a standard edge widget and adds it to the square
   * at a specific horizontal edge.
   * 
   * @param loc The location of this vertical edge. A {@link Vec2i} where the 
   *            x coordinate is a vertex coordinate and the y coordinate is a cell coordinate.
   *            (See {@link Recti} for details on cells and vertices.) 
   * @param thickness The desired thickness for this edge (in pixels).
   * @param styleNames A list of string containing all the desired style names. (The default edge style is added automatically.)
   * @return The newly created widget.
   */
  public Widget createVerticalEdge( Vec2i loc, int thickness, String... styleNames ) {
    Widget widget = GWT.create( FlowPanel.class );
    widget.addStyleName( resources.style().edge() );
    for( String style : styleNames )
      widget.addStyleName( style );
    addVerticalEdgeWidget( loc, widget, thickness );
    return widget;
  }

  /**
   * Creates a standard cell widget and adds it at a specific cell.
   * (See {@link Recti} for details on cells and vertices.) 
   * 
   * @param loc The location of this cell (a {@link Vec2i}). 
   * @param styleNames A list of string containing all the desired style names. (The default cell style is added automatically.)
   * @return The newly created widget.
   */
  public Widget createCell( Vec2i loc, String... styleNames ) {
    Widget widget = GWT.create( FlowPanel.class );
    widget.addStyleName( resources.style().cell() );
    for( String style : styleNames )
      widget.addStyleName( style );
    addCellWidget( loc, widget );
    return widget;
  }

  /**
   * Creates a standard selected cell widget and adds it at a specific cell.
   * (See {@link Recti} for details on cells and vertices.) 
   * 
   * @param loc The location of this cell (a {@link Vec2i}). 
   * @param styleNames A list of string containing all the desired style names. (The default selected cell style is added automatically.)
   * @return The newly created widget.
   */
  public Widget createSelectedCell( Vec2i loc, String... styleNames ) {
    Widget widget = GWT.create( FlowPanel.class );
    widget.addStyleName( resources.style().selectedCell() );
    for( String style : styleNames )
      widget.addStyleName( style );
    addCellWidget( loc, widget );
    return widget;
  }

  /**
   * Creates and adds all the inner horizontal and vertical edges to draw 
   * a regular grid. 
   * 
   * @param thickness The desired thickness of the inner edges.
   * @param styleNames A list of string containing all the desired style names. (The default edge style is added automatically.)
   * @return A list of all newly created widgets.
   */
  public List<Widget> createInnerEdges( int thickness, String... styleNames ) {
    Vec2i loc = new Vec2i();
    List<Widget> result = new ArrayList<Widget>();
    for( int y=0; y<height; ++y ) {
      loc.y = y;
      for( int x=0; x<width; ++x ) {
        loc.x = x;
        if( x > 0 )
          result.add( createVerticalEdge( loc, thickness, styleNames) );
        if( y > 0)
          result.add( createHorizontalEdge( loc, thickness, styleNames) );
      }
    }
    return result;
  }

  /**
   * Creates and adds all the outer horizontal and vertical edges to draw 
   * a regular grid. 
   * 
   * @param thickness The desired thickness of the inner edges.
   * @param styleNames A list of string containing all the desired style names. (The default edge style is added automatically.)
   * @return A list of all newly created widgets.
   */
  public List<Widget> createOuterEdges( int thickness, String... styleNames ) {
    Vec2i loc = new Vec2i();
    List<Widget> result = new ArrayList<Widget>();
    for( int y=0; y<=height; y += height ) {
      loc.y = y;
      for( int x=0; x<width; ++x ) {
        loc.x = x;
        result.add( createHorizontalEdge( loc, thickness, styleNames) );
      }
    }
    for( int x=0; x<=width; x+=width ) {
      loc.x = x;
      for( int y=0; y<height; ++y ) {
        loc.y = y;
        result.add( createVerticalEdge( loc, thickness, styleNames) );
      }
    }
    return result;
  }

  /**
   * Clears all the content and creates an {@link OverflowLayoutPanel} for every cell.
   * In addition, similar {@link OverflowLayoutPanel}s are created pass the right-hand
   * column and pass the bottom line. This makes it possible to draw edges and vertices
   * element on the right and bottom edge of the puzzle.   
   */
  private void createCellPanels() {
    final int precision = 1024;
    squareGridContainer.clear();
    cells = new CellLayoutPanel[width+1][height+1];
    for( int y=0; y <= height; ++y ) {
      int percentY = y*precision/height;
      int percentNextYBottom = precision - (y+1)*precision/height;
      for( int x=0; x <= width; ++x ) {
        int percentX = x*precision/width;
        int percentNextXRight = precision - (x+1)*precision/width;
        CellLayoutPanel cell = GWT.create( CellLayoutPanel.class );
        cells[x][y] = cell;        
        squareGridContainer.add( cell );       
        squareGridContainer.setWidgetLeftRight(cell, percentX*100.0/precision, Unit.PCT, percentNextXRight*100.0/precision, Unit.PCT);
        squareGridContainer.setWidgetTopBottom(cell, percentY*100.0/precision, Unit.PCT, percentNextYBottom*100.0/precision, Unit.PCT);
      }    
    }
  }


}
