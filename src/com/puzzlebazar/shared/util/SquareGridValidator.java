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
 * Interface for a class that can check the validity of cell, vertices and
 * edge coordinates within a {@link Has2DSize}.
 * 
 * @author Philippe Beaudoin
 */
public interface SquareGridValidator {

  /**
   * Checks if the passed coordinate is a valid cell coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param x The x cell coordinate.
   * @param y The y cell coordinate.
   * @return {@code true} if the passed coordinate is a valid cell coordinate, {@code false} otherwise.
   */
   boolean isValidCell(int x, int y);

  /**
   * Checks if the passed coordinate is a valid cell coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param cell The cell coordinate.
   * @return {@code true} if the passed coordinate is a valid cell coordinate, {@code false} otherwise.
   */
  boolean isValidCell(Vec2i cell);

  /**
   * Checks if the passed coordinate is a valid vertical edge coordinate.
   * It must have a valid vertex x-coordinate and a valid cell y-coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param x The x vertical edge coordinate.
   * @param y The y vertical edge coordinate.
   * @return {@code true} if the passed coordinate is a valid vertical edge coordinate, {@code false} otherwise.
   */
  boolean isValidVerticalEdge(int x, int y);

  /**
   * Checks if the passed coordinate is a valid vertical edge coordinate.
   * It must have a valid vertex x-coordinate and a valid cell y-coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param edge The vertical edge coordinate.
   * @return {@code true} if the passed coordinate is a valid vertical edge coordinate, {@code false} otherwise.
   */
  boolean isValidVerticalEdge(Vec2i edge);

  /**
   * Checks if the passed coordinate is a valid horizontal edge coordinate.
   * It must have a valid cell x-coordinate and a valid vertex y-coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param x The x horizontal edge coordinate.
   * @param y The y horizontal edge coordinate.
   * @return {@code true} if the passed coordinate is a valid horizontal edge coordinate, {@code false} otherwise.
   */
  boolean isValidHorizontalEdge(int x, int y);

  /**
   * Checks if the passed coordinate is a valid horizontal edge coordinate.
   * It must have a valid cell x-coordinate and a valid vertex y-coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param edge The horizontal edge coordinate.
   * @return {@code true} if the passed coordinate is a valid horizontal edge coordinate, {@code false} otherwise.
   */
  boolean isValidHorizontalEdge(Vec2i edge);

  /**
   * Checks if the passed coordinate is a valid vertex coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param x The x vertex coordinate.
   * @param y The y vertex coordinate.
   * @return {@code true} if the passed coordinate is a valid vertex coordinate, {@code false} otherwise.
   */
  boolean isValidVertex(int x, int y);

  /**
   * Checks if the passed coordinate is a valid vertex coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param vertex The vertex coordinate.
   * @return {@code true} if the passed coordinate is a valid vertex coordinate, {@code false} otherwise.
   */
  boolean isValidVertex(Vec2i vertex);

}