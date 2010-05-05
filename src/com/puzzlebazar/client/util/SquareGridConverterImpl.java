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

package com.puzzlebazar.client.util;

import com.google.gwt.user.client.ui.Widget;
import com.puzzlebazar.client.ui.SquareGridLayoutPanel;
import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

public class SquareGridConverterImpl implements SquareGridConverter {

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
  public SquareGridConverterImpl( SquareGridLayoutPanel gridPanel, Widget uiWidget ) {
    this.gridPanel = gridPanel;
    this.uiWidget = uiWidget;
  }

  @Override
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

  @Override
  public VertexHitInfo pixelToVertex( int x, int y ) {

    updateGridInUiWidget();
    double pixelPerCellX = gridInUiWidget.w/(double)gridPanel.getWidth();
    double pixelPerCellY = gridInUiWidget.h/(double)gridPanel.getHeight();

    // Move grid half a cell to the top-left
    double dx = (x - gridInUiWidget.x + pixelPerCellX/2.0);
    double dy = (y - gridInUiWidget.y + pixelPerCellY/2.0);
    if( dx<0 || dy<0 )
      return null;

    Vec2i vertex = new Vec2i();
    Vec2i dist = new Vec2i();
    vertex.x = (int)(dx * gridPanel.getWidth() / gridInUiWidget.w);
    vertex.y = (int)(dy * gridPanel.getHeight() / gridInUiWidget.h);

    // The vertex position
    dist.x = Math.abs((int)(dx - vertex.x * pixelPerCellX - pixelPerCellX/2.0));
    dist.y = Math.abs((int)(dy - vertex.y * pixelPerCellY - pixelPerCellY/2.0));

    return new VertexHitInfo(vertex, dist);
  }

  @Override
  public EdgeHitInfo pixelToEdge( int x, int y ) {
    updateGridInUiWidget();
    Vec2i cell = pixelToCell( x, y );
    VertexHitInfo vertexHitInfo = pixelToVertex(x, y);
    if( vertexHitInfo == null || vertexHitInfo.getVertex().x < 0 ||  vertexHitInfo.getVertex().y < 0 )
      return null;

    boolean vertical;
    Vec2i edge = new Vec2i();
    int dist;
    if( vertexHitInfo.getDist().y < vertexHitInfo.getDist().x ) {
      // Select horizontal edge
      vertical = false;
      edge.x = cell.x;
      edge.y = vertexHitInfo.getVertex().y;
      dist = vertexHitInfo.getDist().y;
    }
    else {
      // Select vertical edge
      vertical = true;
      edge.x = vertexHitInfo.getVertex().x;
      edge.y = cell.y;
      dist = vertexHitInfo.getDist().x;
    }

    return new EdgeHitInfo(vertical, edge, dist);
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
