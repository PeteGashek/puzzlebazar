package com.puzzlebazar.shared.util;

/**
 * The simple interface of any object that has a discrete size. 
 * 
 * @author Philippe Beaudoin
 */
public interface Has2DSize {

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
