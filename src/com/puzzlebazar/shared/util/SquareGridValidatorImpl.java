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

package com.puzzlebazar.shared.util;

/**
 * @author Philippe Beaudoin
 */
public class SquareGridValidatorImpl implements SquareGridValidator {

  private final Has2DSize squareGrid;
  
  /**
   * Creates a validator attached to the specified {@link Has2DSize}.
   * 
   * @param squareGrid The attached square grid, which must implement {@link Has2DSize}.
   */
  public SquareGridValidatorImpl(Has2DSize squareGrid) {
    this.squareGrid = squareGrid;
  }

  @Override
  public boolean isValidCell(int x, int y) {
    return 0 <= x && x < squareGrid.getWidth() &&
           0 <= y && y < squareGrid.getHeight();
  }

  @Override
  public boolean isValidCell(Vec2i cell) {
    return isValidCell(cell.x, cell.y);
  }

  @Override
  public boolean isValidVerticalEdge(int x, int y) {
    return 0 <= x && x <= squareGrid.getWidth() &&
           0 <= y && y < squareGrid.getHeight();
  }
  
  @Override
  public boolean isValidVerticalEdge(Vec2i edge) {
    return isValidVerticalEdge(edge.x, edge.y);
  }

  @Override
  public boolean isValidHorizontalEdge(int x, int y) {
    return 0 <= x && x < squareGrid.getWidth() &&
           0 <= y && y <= squareGrid.getHeight();
  }
  
  @Override
  public boolean isValidHorizontalEdge(Vec2i edge) {
    return isValidHorizontalEdge(edge.x, edge.y);
  }

  @Override
  public boolean isValidVertex(int x, int y) {
    return 0 <= x && x <= squareGrid.getWidth() &&
           0 <= y && y <= squareGrid.getHeight();
  }
  
  @Override
  public boolean isValidVertex(Vec2i vertex) {
    return isValidVertex(vertex.x, vertex.y);
  }
  
}
