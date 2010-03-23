package com.puzzlebazar.client.util;

import com.google.gwt.user.client.ui.Widget;
import com.puzzlebazar.client.ui.SquareGridLayoutPanel;

/**
 * A utility class to convert different units (pixel coordinates,
 * cell coordinates, etc.) between a {@link SquareGridLayoutPanel} 
 * and another {@link Widget} to which all the ui events are sent.
 * 
 * @author Philippe Beaudoin
 */
public class SquareGridConverter {

  public class VertexInfo {

    /**
     * The coordinates of the edge.
     */
    public final Vec2i vertex;  
    
    /**
     * The (unsigned) distance between the point and the vertex (in pixels).
     */
    public final Vec2i dist;
    
    /**
     * Creates information about an invalid vertex.
     */
    public VertexInfo() {
      vertex = new Vec2i(-1,-1);
      dist = new Vec2i(0,0);
    }
    
  }
  
  public class EdgeInfo {
    /**
     * {@code true} if the edge is vertical, {@code false} if it is horizontal.
     */
    public boolean isVertical;

    /**
     * The coordinates of the edge.
     */
    public final Vec2i edge;  
    
    /**
     * The (unsigned) distance between the point and the edge (in pixels).
     */
    public int dist;
    
    /**
     * Creates information about an invalid edge.
     */
    public EdgeInfo() {
      isVertical = false;
      edge = new Vec2i(-1,-1);
      dist = 0;
    }
    
  }

  private final SquareGridLayoutPanel gridPanel;
  private final Widget uiWidget;
  
  /**
   * Contains the position of the square grid in
   * the uiWidget coordinates. (Borders are already removed.)
   */
  private final Recti gridInUiWidget = new Recti();
  
  /**
   * Creates a converter between a specific {@link SquareGridLayoutPanel}
   * and another {@link Widget} in which all the ui events will be passed.
   * 
   * @param gridPanel The {@link SquareGridLayoutPanel}.
   * @param uiWidget The {@link Widget} that will be receiving the ui events.
   */
  public SquareGridConverter( SquareGridLayoutPanel gridPanel, Widget uiWidget ) {
    this.gridPanel = gridPanel;
    this.uiWidget = uiWidget;
  }
  
  /**
   * Converts a position in pixel (within the ui widget) to
   * a cell coordinate within the square grid. Does not
   * perform any validity check.
   * 
   * @param x The pixel x coordinate within the ui widget.
   * @param y The pixel y coordinate within the ui widget.
   * @return The cell coordinate if the coordinates are within the grid, 
   *         otherwise returns invalid (but undefined) coordinates.
   */
  public Vec2i pixelToCell( int x, int y ) {
    updateGridInUiWidget();
    int dx = (x - gridInUiWidget.x);
    int dy = (y - gridInUiWidget.y);
    Vec2i result = new Vec2i(
        dx * gridPanel.getWidth() / gridInUiWidget.w,
        dy * gridPanel.getHeight() / gridInUiWidget.h );
    if( dx<0 )
      result.x = -1;
    if( dy<0 )
      result.y = -1;
    return result;
  }

  /**
   * Converts a position in pixel (within the ui widget) to
   * a vertex coordinate within the square grid. Does not
   * perform any validity check.
   * 
   * @param x The pixel x coordinate within the ui widget.
   * @param y The pixel y coordinate within the ui widget.
   * @return Information on the closest edge if the coordinates are within the grid or within
   *         half a grid cell of the borders. Otherwise returns invalid (but undefined) coordinates.
   */
  public VertexInfo pixelToVertex( int x, int y ) {

    updateGridInUiWidget();
    VertexInfo result = new VertexInfo();
    double pixelPerCellX = gridInUiWidget.w/(double)gridPanel.getWidth();
    double pixelPerCellY = gridInUiWidget.h/(double)gridPanel.getHeight();
    
    // Move grid half a cell to the top-left
    double dx = (x - gridInUiWidget.x + pixelPerCellX/2.0);
    double dy = (y - gridInUiWidget.y + pixelPerCellY/2.0);
    if( dx<0 || dy<0 )
      return result;
    
    result.vertex.x = (int)(dx * gridPanel.getWidth() / gridInUiWidget.w);
    result.vertex.y = (int)(dy * gridPanel.getHeight() / gridInUiWidget.h);
 
    // The vertex position
    result.dist.x = Math.abs((int)(dx - result.vertex.x * pixelPerCellX - pixelPerCellX/2.0));
    result.dist.y = Math.abs((int)(dy - result.vertex.y * pixelPerCellY - pixelPerCellY/2.0));
    
    return result;
  }
  
  /**
   * Converts a position in pixel (within the ui widget) to
   * an edge coordinate within the square grid. Does not
   * perform any validity check.
   * 
   * @param x The pixel x coordinate within the ui widget.
   * @param y The pixel y coordinate within the ui widget.
   * @return Information on the closest vertex if the coordinates are within the grid, or within
   *         half a grid cell of the borders. Otherwise returns invalid (but undefined) coordinates.
   */
  public EdgeInfo pixelToEdge( int x, int y ) {
    updateGridInUiWidget();
    Vec2i cell = pixelToCell( x, y );
    VertexInfo vertexInfo = pixelToVertex(x, y);
    EdgeInfo result = new EdgeInfo();
    if( vertexInfo.vertex.x < 0 ||  vertexInfo.vertex.y < 0 )
      return result;

    if( vertexInfo.dist.y < vertexInfo.dist.x ) {
      // Select horizontal edge
      result.isVertical = false;
      result.edge.x = cell.x;
      result.edge.y = vertexInfo.vertex.y;
      result.dist = vertexInfo.dist.y;
    }
    else {
      // Select vertical edge
      result.isVertical = true;
      result.edge.x = vertexInfo.vertex.x;
      result.edge.y = cell.y;
      result.dist = vertexInfo.dist.x;
    }
    
    return result;
  }

  /**
   * Makes sure the {@link gridInWidget} rectangle is up to date.
   * Must be called before any conversion operation.
   * Possible optimization: 
   * We could make the user responsible of calling
   * this method when the window is resized. 
   */
  private void updateGridInUiWidget() {
    int border = gridPanel.getBorder();
    gridInUiWidget.x = gridPanel.getAbsoluteLeft()+border-uiWidget.getAbsoluteLeft();
    gridInUiWidget.y = gridPanel.getAbsoluteTop()+border-uiWidget.getAbsoluteTop();
    gridInUiWidget.w = gridPanel.getOffsetWidth()-2*border;
    gridInUiWidget.h = gridPanel.getOffsetHeight()-2*border;
  }

}
