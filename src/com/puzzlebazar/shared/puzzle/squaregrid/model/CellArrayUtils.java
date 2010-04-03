package com.puzzlebazar.shared.puzzle.squaregrid.model;

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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.puzzlebazar.shared.util.InvalidCoordinateException;
import com.puzzlebazar.shared.util.Vec2i;

/**
 * This class provides a number of useful static methods for checking the validity
 * of a number of different puzzles that use cell arrays.
 * 
 * @author Philippe Beaudoin
 */
public class CellArrayUtils {

  /**
   * Check if there exists a disconnected group of cells of the specified states. Cells are
   * in the same group if they are adjacent to one another (horizontally or vertically).
   * A group is disconnected if the cell array contains at least one cell of the desired type
   * that is not within the group. If many disconnected groups exist, only one is returned
   * (arbitrarily).
   * 
   * @param cellArray The {@link CellArray} to check.
   * @param states An array of all the states which should form a connected group. 
   * @return {@code null} if all the cells of the desired type form a connected group, otherwise return a {@link List} of cell
   *         locations that form a disconnected group.
   */
  public static List<Vec2i> findDisconnectedGroup( CellArray cellArray, int[] states ) {
    return findDisconnectedGroup( cellArray, cellsMatching(cellArray, states) );
  }

  /**
   * @see #findDisconnectedGroup(CellArray, int[])
   */
  private static List<Vec2i> findDisconnectedGroup( CellArray cellArray, boolean[][] valid ) {
    int width = cellArray.getWidth(); 
    int height = cellArray.getHeight(); 
    if( width <= 0 || height <= 0 ) 
      return null;

    boolean visited[][] = new boolean[width][height];
    for( int i=0; i<visited.length; ++i)
      Arrays.fill( visited[i], false );

    Vec2i loc = findValid(cellArray, valid);
    if( loc == null ) 
      return null;

    List<Vec2i> stack = new ArrayList<Vec2i>();
    stack.add(loc);
    visited[loc.x][loc.y] = true;
    while( !stack.isEmpty() ) {
      loc = stack.remove( stack.size()-1 );
      loc.x -= 1;
      if( loc.x>=0 ) 
        checkAndAdd(visited, valid, loc, stack);
      loc.x += 2;
      if( loc.x<visited.length ) 
        checkAndAdd(visited, valid, loc, stack);
      loc.x -= 1;
      loc.y -= 1;
      if( loc.y>=0 ) 
        checkAndAdd(visited, valid, loc, stack);
      loc.y += 2;
      if( loc.y<visited[0].length ) 
        checkAndAdd(visited, valid, loc, stack);
    }

    if( anyValidVisited(true, false, valid, visited) )
      return findValidVisited(true, true, valid, visited);

    return null;
  }


  /**
   * Private method used by {@link #areCellsInAConnectedGroup(CellArray, boolean[][])}.
   */
  private static void checkAndAdd(boolean[][] visited, boolean[][] valid, Vec2i loc,
      List<Vec2i> stack) {
    if( !visited[loc.x][loc.y] && valid[loc.x][loc.y] ) {
      stack.add( new Vec2i(loc) );
      visited[loc.x][loc.y] = true;
    }
  }

  /**
   * Check if there exists at two adjacent cells (horizontally or vertically) of the given states.
   * The first such such pair found is returned.
   * 
   * @param cellArray The {@link CellArray} to check.
   * @param states An array of all the states which should form a connected group. 
   * @return {@code null} if all the cells of the desired type are non-adjacent, otherwise return a {@link List} of 2
   *         adjacent cells of the desired type.
   */
  public static List<Vec2i> findTwoAdjacentCells( CellArray cellArray, int[] states ) {
    return findTwoAdjacentCells( cellArray, cellsMatching(cellArray, states) );
  }

  /**
   * @see #findTwoAdjacentCells(CellArray, int[])
   */
  public static List<Vec2i> findTwoAdjacentCells( CellArray cellArray, boolean[][] valid ) {

    int width = cellArray.getWidth(); 
    int height = cellArray.getHeight(); 
    if( width <= 0 || height <= 0 ) 
      return null;

    for( int i=0; i<valid.length-1; ++i ) {
      boolean prevValid = false;
      for( int j=0; j<valid[i].length; ++j ) {
        if( valid[i][j] ) {
          if( prevValid ) {
            List<Vec2i> result = new ArrayList<Vec2i>(2); 
            result.add( new Vec2i(i,j) );
            result.add( new Vec2i(i,j-1) );
            return result;
          }
          prevValid = true;
          if( valid[i+1][j] ) {
            List<Vec2i> result = new ArrayList<Vec2i>(2); 
            result.add( new Vec2i(i,j) );
            result.add( new Vec2i(i+1,j) );
            return result;
          }
        }
        else
          prevValid = false;
      }
    }

    return null;

  }

  /**
   * Create a 2D boolean array where each location is true if the corresponding cell
   * contains a state matching one of the desired states.
   * 
   * @param cellArray The {@link CellArray} to use.
   * @param desiredStates The set of all cells to look for, an array of integers.
   * @return A 2D boolean array containing {@code true} at every location where a cell
   *         state matches one of the desired states.
   */
  private static boolean[][] cellsMatching(CellArray cellArray, int[] desiredStates) {

    boolean[][] result = new boolean[cellArray.getWidth()][cellArray.getHeight()];
    Vec2i loc = new Vec2i();
    for( int i=0; i<result.length; ++i ) {
      loc.x = i;
      for( int j=0; j<result[i].length; ++j ) {
        loc.y = j;
        try {
          result[i][j] = stateIn( cellArray.getCellState(loc), desiredStates );
        } catch (InvalidCoordinateException e) {
          assert false : "Unexpected InvalidCoordinateException.";
        }
      }
    }

    return result;
  }

  /**
   * Returns one valid cell, or null if no such cell exist.
   * 
   * @param cellArray The {@link CellArray} to use.
   * @param valid An array containing {@link true} at each location of a valid cell.
   * @return The cell coordinates of a valid cell, or {@code null} if no such cell is found.
   */
  private static Vec2i findValid(CellArray cellArray, boolean[][] valid) {

    for( int i=0; i<valid.length; ++i ) {
      for( int j=0; j<valid[i].length; ++j ) {
        if( valid[i][j] ) return new Vec2i(i,j);
      }
    }

    return null;
  }


  /**
   * Checks that the passed state is found in the list of states.
   * 
   * @param state State to look for, an integer.
   * @param states List of desired states.
   * @return {@code true} if state is in list of desired states, {@code false} otherwise.
   */
  private static boolean stateIn(int state, int[] states) {
    for( int i = 0; i < states.length; ++i ) {
      if( state == states[i] ) return true;
    }
    return false;
  }

  /**
   * Check if there exist at least one cell that is both valid/invalid and visited/non-visited.
   * 
   * @param valid {@true} if you're interested in valid cells, {@false} for invalid cells.
   * @param visited {@true} if you're interested in visited cells, {@false} for non-visited cells.
   * @param validCells 2D array of valid cells.
   * @param visitedCells  2D array of visited cells.
   * @return The {@link List} of all cells that are both valid/invalid and visited/non-visited.
   */
  public static boolean anyValidVisited(
      boolean valid,
      boolean visited,
      boolean[][] validCells, 
      boolean[][] visitedCells) {

    Vec2i loc = new Vec2i();
    for( int i=0; i<validCells.length; ++i) {
      loc.x = i;
      for( int j=0; j<validCells[i].length; ++j) {
        loc.y = j;
        if( (validCells[i][j] ^ !valid) && (visitedCells[i][j] ^ !visited) )
          return true;
      }
    }

    return false;
  }


  /**
   * Find all the cells that are both valid/invalid and visited/non-visited.
   * 
   * @param valid {@true} if you're interested in valid cells, {@false} for invalid cells.
   * @param visited {@true} if you're interested in visited cells, {@false} for non-visited cells.
   * @param validCells 2D array of valid cells.
   * @param visitedCells 2D array of visited cells.
   * @return The {@link List} of all cells that are both valid/invalid and visited/non-visited.
   */
  public static List<Vec2i> findValidVisited(
      boolean valid,
      boolean visited,
      boolean[][] validCells, 
      boolean[][] visitedCells) {

    List<Vec2i> result = new ArrayList<Vec2i>();

    Vec2i loc = new Vec2i();
    for( int i=0; i<validCells.length; ++i) {
      loc.x = i;
      for( int j=0; j<validCells[i].length; ++j) {
        loc.y = j;
        if( (validCells[i][j] ^ !valid) && (visitedCells[i][j] ^ !visited) )
          result.add( loc );
      }
    }

    return result;
  }

}
