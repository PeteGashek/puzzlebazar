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

import java.io.Serializable;

/**
 * A simple class to manipulate rectangles aligned on integer coordinates.
 * When using integer rectangles, it is important to distinguish between the
 * notion of a cell and a vertex. Here is an example:
 * <pre>
 *   Recti r = new Recti(3,1,4,1);
 *   
 * 
 *     x  .  .  .  .  .  .  .  .  .  .
 *      O
 *     .  .  .  +-----------+  .  .  .
 *              |           |
 *     .  .  .  +-----------+  .  .  .
 *     
 *     .  .  .  .  .  .  .  .  .  .  .
 *  
 *  x = vertex at 0,0
 *  O = cell at 0,0
 *
 *  r.getPt00();   // ==> (3,1)
 *  r.getPt11();   // ==> (7,2)
 *  r.getCell00(); // ==> (3,1)
 *  r.getCell11(); // ==> (6,1)
 *  </pre>
 *  
 * 
 * @author Philippe Beaudoin
 */
public class Recti implements Serializable {

  private static final long serialVersionUID = 694706265503512387L;

  public int x;
  public int y;
  public int w;
  public int h;

  /**
   * Creates a zero-sized rectangle with the top-left coordinate at (0,0).
   */
  public Recti() {
    x = 0;
    y = 0;
    w = 0;
    h = 0;
  }

  /**
   * Creates a rectangle with the specified size and top-left coordinate.
   * 
   * @param x The left coordinate.
   * @param y The top coordinate.
   * @param w The width.
   * @param h The height.
   */
  public Recti(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  /**
   * Copy-constructor for a rectangle.
   * 
   * @param from The rectangle to copy.
   */
  public Recti(Recti from) {
    x = from.x;
    y = from.y;
    w = from.w;
    h = from.h;
  }

  /**
   * Creates a rectangle given the location of two diagonally opposed cells (or vertices). The 
   * first cell (vertex) can be any of the four corners of the rectangle, as long as the other is
   * diagonally opposite. The rectangle produced include both corner cells (or vertices).
   * 
   * @param loc1 First cell (or vertex) of the rectangle
   * @param loc2 Diagonally opposed cell (or vertex)
   */
  public Recti(Vec2i loc1, Vec2i loc2) {
    x = Math.min(loc1.x, loc2.x);
    y = Math.min(loc1.y, loc2.y);
    w = Math.abs(loc2.x - loc1.x) + 1;
    h = Math.abs(loc2.y - loc1.y) + 1;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + h;
    result = prime * result + w;
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Recti other = (Recti) obj;
    if (h != other.h) {
      return false;
    }
    if (w != other.w) {
      return false;
    }
    if (x != other.x) {
      return false;
    }
    if (y != other.y) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return "((" + x + ", " + y + "), (" + w + ", " + h + "))";
  }

  /**
   * Modifies this rectangle so that it is a copy of the passed rectangle.
   * 
   * @param rect The rectangle to copy (a {@link Recti}).
   */
  public void copy(Recti rect) {
    x = rect.x;
    y = rect.y;
    w = rect.w;
    h = rect.h;
  }

  /**
   * Access the location of the top-left corner vertex.
   * (See {@link Recti} for the difference between vertices and cells.)
   * 
   * @return The location of the top-left corner vertex (a {@link Vec2i}).
   */
  public Vec2i getPt00() {
    return new Vec2i(x, y);
  }

  /**
   * Access the location of the top-right corner vertex.
   * (See {@link Recti} for the difference between vertices and cells.)
   * 
   * @return The location of the top-right corner vertex (a {@link Vec2i}).
   */
  public Vec2i getPt10() {
    return new Vec2i(x + w, y);
  }

  /**
   * Access the location of the bottom-left corner vertex.
   * (See {@link Recti} for the difference between vertices and cells.)
   * 
   * @return The location of the bottom-left corner vertex (a {@link Vec2i}).
   */
  public Vec2i getPt01() {
    return new Vec2i(x, y + h);
  }

  /**
   * Access the location of the bottom-right corner vertex.
   * (See {@link Recti} for the difference between vertices and cells.)
   * 
   * @return The location of the bottom-right corner vertex (a {@link Vec2i}).
   */
  public Vec2i getPt11() {
    return new Vec2i(x + w, y + h);
  }

  /**
   * Access the location of the top-left corner cell.
   * (See {@link Recti} for the difference between vertices and cells.)
   * 
   * @return The location of the top-left corner cell (a {@link Vec2i}).
   */
  public Vec2i getCell00() {
    return new Vec2i(x, y);
  }

  /**
   * Access the location of the top-right corner cell.
   * (See {@link Recti} for the difference between vertices and cells.)
   * 
   * @return The location of the top-right corner cell (a {@link Vec2i}).
   */
  public Vec2i getCell10() {
    return new Vec2i(x + w - 1, y);
  }

  /**
   * Access the location of the bottom-left corner cell.
   * (See {@link Recti} for the difference between vertices and cells.)
   * 
   * @return The location of the bottom-left corner cell (a {@link Vec2i}).
   */
  public Vec2i getCell01() {
    return new Vec2i(x, y + h - 1);
  }

  /**
   * Access the location of the bottom-right corner cell.
   * (See {@link Recti} for the difference between vertices and cells.)
   * 
   * @return The location of the bottom-right corner cell (a {@link Vec2i}).
   */
  public Vec2i getCell11() {
    return new Vec2i(x + w - 1, y + h - 1);
  }

  /**
   * Checks if the cells of two rectangles overlap. Two rectangles overlapping only on a vertex
   * or along an edge are not considered to be overlapping by this function.
   * 
   * @param rect The rectangle to check against (a {@link Recti}).
   * @return {@code true} if the rectangles overlap, {@code false} otherwise.
   */
  public boolean cellsOverlap(Recti rect) {

    int left = x;
    int right = x + w - 1;
    int top = y;
    int bottom = y + h - 1;

    int bitfield = computeBitfield(rect.x, rect.y, left, right, top, bottom);
    bitfield &= computeBitfield(rect.x + rect.w - 1, rect.y, left, right, top, bottom);
    bitfield &= computeBitfield(rect.x, rect.y + rect.h - 1, left, right, top, bottom);
    bitfield &= computeBitfield(rect.x + rect.w - 1, rect.y + rect.h - 1, left, right, top, bottom);

    return bitfield == 0;
  }

  /**
   * Returns the Cohen-Sutherland bitfield code for point x, y.
   * <ul>
   * <li>Bit 0: Vertex is left of the left edge</li>
   * <li>Bit 1: Vertex is right of the right edge</li>
   * <li>Bit 2: Vertex is above top edge</li>
   * <li>Bit 3: Vertex is below bottom edge</li>
   * </ul>
   * 
   * @param x  X-coord of point to check.
   * @param y  Y-coord of point to check.
   * @param left  Left coord of window.
   * @param right Right coord of window.
   * @param top Top coord of window.
   * @param bottom Bottom coord of window.
   * @return The bitfield code.
   */
  private int computeBitfield(int x, int y, int left, int right, int top, int bottom) {
    int bitfield = 0;
    if (x < left) {
      bitfield |= 1;
    }
    if (x > right) {
      bitfield |= 2;
    }
    if (y < top) {
      bitfield |= 4;
    }
    if (y > bottom) {
      bitfield |= 8;
    }
    return bitfield;
  }

  /**
   * Checks if a given cell is contained with the rectangle.
   * 
   * @param cellLoc The location of the cell to check (a {@link Vec2i}).
   * @return {@code true} if the passed cell is inside the rectangle, {@code false} otherwise.
   */
  public boolean containsCell(Vec2i cellLoc) {
    return 
    cellLoc.x >= x && cellLoc.x < x + w &&
    cellLoc.y >= y && cellLoc.y < y + h;
  }

  /**
   * <b>Important!</b> This method expects location of cells in parameters
   * and outputs location of vertices. If you're working only with cells
   * or vertices use {@link #getTopLeft(Vec2i, Vec2i)} instead.
   * (See {@link Recti} for the difference between vertices and cells.)
   * <p />
   * Given the locations of two diagonally opposite <i>cells</i> of a rectangle, 
   * finds the location of the top-left <i>vertex</i>.
   * 
   * @param cellLoc The location of a cell at one corner of the rectangle (a {@link Vec2i}). 
   * @param oppositeCellLoc The location of the cell at the diagonally opposite corner of the rectangle (a {@link Vec2i}). 
   * @return The location of the top-left vertex (a {@link Vec2i}).
   */
  public static Vec2i getTopLeftVertex(Vec2i cellLoc, Vec2i oppositeCellLoc) {
    return new Vec2i(
        Math.min(cellLoc.x, oppositeCellLoc.x),
        Math.min(cellLoc.y, oppositeCellLoc.y));
  }

  /**
   * <b>Important!</b> This method expects location of cells in parameters
   * and outputs location of vertices. If you're working only with cells
   * or vertices use {@link #getBottomRight(Vec2i, Vec2i)} instead.
   * (See {@link Recti} for the difference between vertices and cells.)
   * <p />
   * Given the locations of two diagonally opposite <i>cells</i> of a rectangle, 
   * finds the location of the bottom-right <i>vertex</i>.
   * 
   * @param cellLoc The location of a cell at one corner of the rectangle (a {@link Vec2i}). 
   * @param oppositeCellLoc The location of the cell at the diagonally opposite corner of the rectangle (a {@link Vec2i}). 
   * @return The location of the bottom-right vertex (a {@link Vec2i}).
   */
  public static Vec2i getBottomRightVertex(Vec2i cellLoc, Vec2i oppositeCellLoc) {
    return new Vec2i(
        Math.max(cellLoc.x, oppositeCellLoc.x) + 1,
        Math.max(cellLoc.y, oppositeCellLoc.y) + 1);
  }

  /**
   * Given the location of two diagonally opposite cells (or vertices) of a rectangle, 
   * finds the location of the top-left cell (or vertex).
   * 
   * @param cellLlocFromoc The location of a cell (or vertex) at one corner of the rectangle (a {@link Vec2i}). 
   * @param oppositeCellLoc The location of the cell (or vertex) at the diagonally opposite corner of the rectangle (a {@link Vec2i}). 
   * @return The location of the top-left cell (or vertex) (a {@link Vec2i}).
   * 
   * @see #getTopLeftVertex(Vec2i, Vec2i)
   */
  public static Vec2i getTopLeft(Vec2i cellLlocFromoc, Vec2i oppositeCellLoc) {
    return getTopLeftVertex(cellLlocFromoc, oppositeCellLoc);
  }

  /**
   * Given the location of two diagonally opposite cells (or vertices) of a rectangle, 
   * finds the location of the bottom-right cell (or vertex).
   * 
   * @param cellLoc The location of a cell (or vertex) at one corner of the rectangle (a {@link Vec2i}). 
   * @param oppositeCellLoc The location of the cell (or vertex) at the diagonally opposite corner of the rectangle (a {@link Vec2i}). 
   * @return The location of the bottom-right cell (or vertex) (a {@link Vec2i}).
   * 
   * @see #getBottomRightVertex(Vec2i, Vec2i)
   */
  public static Vec2i getBottomRight(Vec2i cellLoc, Vec2i oppositeCellLoc) {
    return new Vec2i(
        Math.max(cellLoc.x, oppositeCellLoc.x),
        Math.max(cellLoc.y, oppositeCellLoc.y));
  }
}