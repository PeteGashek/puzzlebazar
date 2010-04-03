package com.puzzlebazar.client.util;

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


import com.google.gwt.user.client.ui.Widget;
import com.puzzlebazar.client.ui.SquareGridLayoutPanel;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * A utility class to convert different units (pixel coordinates,
 * cell coordinates, etc.) between a {@link SquareGridLayoutPanel} 
 * and another {@link Widget} to which all the ui events are sent.
 * 
 * @author Philippe Beaudoin
 */
public interface SquareGridConverter {

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
  public Vec2i pixelToCell(int x, int y);

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
  public VertexHitInfo pixelToVertex(int x, int y);

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
  public EdgeHitInfo pixelToEdge(int x, int y);

}