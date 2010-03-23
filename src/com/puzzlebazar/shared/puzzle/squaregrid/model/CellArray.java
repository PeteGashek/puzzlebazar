package com.puzzlebazar.shared.puzzle.squaregrid.model;

import com.puzzlebazar.shared.util.Has2DSize;
import com.puzzlebazar.shared.util.InvalidCoordinateException;
import com.puzzlebazar.shared.util.Vec2i;


/**
 * An array of states attached to cells. A state is simply an integer value.
 * 
 * @author Philippe Beaudoin
 */
public interface CellArray extends Has2DSize {
  
  /**
   * Changes the state at the provided cell location.
   * Will fire a {@link SetCellStateEvent}.
   * 
   * @param cell Cell coordinate at which to set the state, a {@link Vec2i}.
   * @param state The desired state, an integer.
   * @throws InvalidCoordinateException If the passed coordinate is not a valid cell coordinate.
   */
  public void setCellState(Vec2i loc, int state) throws InvalidCoordinateException;

  
  /**
   * Access the state at the provided cell location.
   * 
   * @param loc Cell coordinate at which to access the state, a {@link Vec2i}.
   * @return The current state at that coordinate, an integer.
   * @throws InvalidCoordinateException If the passed coordinate is not a valid cell coordinate.
   */
  public int getCellState(Vec2i loc) throws InvalidCoordinateException;
  
  
  /**
   * Access to the number of different states in this model. All the states must be
   * continuous starting from zero. State 0 is counted (hence, a model with state 0,1,2 should
   * return 3 as the number of states)
   * 
   * @return The number of different states in this model
   */
  public int getNbCellStates();

  /**
   * Access the default state.
   * 
   * @return The default state, an integer.
   */
  public int getDefaultCellState();

  /**
   * Resets all the states in the grid to their default value.
   * Will fire a {@link ClearCellsStateEvent}.
   */
  public void clearCellStates();



}

