package com.puzzlebazar.client.util;

import java.io.Serializable;

import com.puzzlebazar.shared.InvalidParameterException;


/**
 * A simple class to manipulate 2D integer vectors.
 * 
 * @author Philippe Beaudoin
 */
public class Vec2i implements Serializable {

  private static final long serialVersionUID = -3061501520823108602L;

  /**
   * Direction that points towards the negative y.
   */
  public final static int NORTH = 0;
  /**
   * Direction that points towards the positive x.
   */
  public final static int EAST  = 1;
  /**
   * Direction that points towards the positive y.
   */
  public final static int SOUTH = 2;
  /**
   * Direction that points towards the negative x.
   */
  public final static int WEST  = 3;

  /**
   * Creates a zero 2D integer vector.
   */
  public Vec2i() {
    this.x = 0;
    this.y = 0;
  }

  /**
   * Creates a 2D integer vector with the specified coordinates. 
   * 
   * @param x The integer x coordinate.
   * @param y The integer y coordinate.
   */
  public Vec2i(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Copy-constructor for a 2D integer vector.
   * 
   * @param from The vector to copy.
   */
  public Vec2i(Vec2i from) {
    if( from == null ) {
      this.x = 0;
      this.y = 0;
    } else
    {
      this.x = from.x;
      this.y = from.y;
    }
  }

  public int x;
  public int y;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Vec2i other = (Vec2i) obj;
    if (x != other.x)
      return false;
    if (y != other.y)
      return false;
    return true;
  }

  /**
   * Advances a 2D integer vector one step in the given direction
   * 
   * @param vec The integer vector (a {@link Vec2i}) to advance. 
   * @param direction The direction to advance in. (One of {@link #NORTH}, 
   * {@link #EAST}, {@link #SOUTH}, {@link #WEST}).
   * @throws InvalidParameterException If the parameter is not a valid direction.
   */
  public static void advanceInDirection(Vec2i vec, int direction) throws InvalidParameterException {
    switch ( direction ) {
    case NORTH: vec.y--; return;
    case EAST: vec.x++; return;
    case SOUTH: vec.y++; return;
    case WEST: vec.x--; return;
    }		
    throw new InvalidParameterException( "Unknown direction, should be NORTH, EAST, SOUTH or WEST." );
  }

  /**
   * Return the direction opposite that passed in parameter.
   * 
   * @param direction The original direction (One of {@link #NORTH}, 
   * {@link #EAST}, {@link #SOUTH}, {@link #WEST}).
   * @return The opposite direction.
   * @throws InvalidParameterException If the parameter is not a valid direction.
   */
  public static int oppositeDirection(int direction) throws InvalidParameterException {
    switch ( direction ) {
    case NORTH: return SOUTH;
    case EAST: return WEST;
    case SOUTH: return NORTH;
    case WEST: return EAST;
    }
    throw new InvalidParameterException( "Unknown direction, should be NORTH, EAST, SOUTH or WEST." );
  }
  
  /**
   * Return the direction ({@link #NORTH}, {@link #EAST}, {@link #SOUTH}, {@link #WEST}) 
   * going from the first integer vector to the second. This works only for vectors that
   * lie in the same line or in the same column (i.e. they share one of their coordinates). 
   * 
   * @param from First integer vector (a {@link Vec2i}).
   * @param to Second integer vector (a {@link Vec2i}).
   * @return The direction from the first location to the second.
   * @throws InvalidParameterException If the integer vectors are neither in the same line nor in the same column.
   */
  public static int findDirection(Vec2i from, Vec2i to) throws Exception {
    int direction = -1;
    if( from.x == to.x ) {
      if( from.y < to.y )
        direction = SOUTH;
      else if( from.y > to.y )
        direction = NORTH;
    }
    else if( from.y == to.y ) {
      if( from.x < to.x )
        direction = EAST;
      else if( from.x > to.x )
        direction = WEST;
    }
    if( direction < 0 ) 
      throw new InvalidParameterException( "Specified integer vectors must lie in the same line or in the same column." );

    return direction;
  }

  /**
   * Modifies this integer vector so that it is a copy of the passed vector.
   * 
   * @param vec Integer vector to copy (a {@link Vec2i}).
   */
  public void copy(Vec2i vec) {
    if( vec == null ) {
      x = 0;
      y = 0;
    }
    x = vec.x;
    y = vec.y;
  }

  /**
   * Evaluates the maximum of the two coordinates.
   * 
   * @return The maximum of {@code x} and {@code y}.
   */
  public int max() {
    return Math.max(x, y);
  }

  /**
   * Evaluates the minimum of the two coordinates.
   * 
   * @return The minimum of {@code x} and {@code y}.
   */
  public int min() {
    return Math.min(x, y);
  }


}