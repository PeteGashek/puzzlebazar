package com.puzzlebazar.client.util;

/**
 * The simple interface of any discrete square grid. 
 * 
 * @author Philippe Beaudoin
 */
public interface SquareGrid {

  /**
   * Access the number of cells in the horizontal direction.
   * 
   * @return The width, that is, the number of cells in the horizontal direction.
   */
  public int getWidth();

  /**
   * Access the number of cells in the vertical direction.
   * 
   * @return The height, that is, the number of cells in the vertical direction.
   */
  public int getHeight();

}
