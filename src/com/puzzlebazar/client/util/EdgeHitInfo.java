package com.puzzlebazar.client.util;

import com.puzzlebazar.shared.util.Vec2i;

/**
 * A class containing information to locate a point relative to an edge
 * in a square grid.
 * 
 * @author Philippe Beaudoin
 */
public class EdgeHitInfo {

  private final boolean vertical;
  private final Vec2i edge;  
  private final int dist;

  /**
   * Creates information about an invalid edge.
   */
  public EdgeHitInfo(
      boolean vertical,
      Vec2i edge,
      int dist ) {
    this.vertical = vertical;
    this.edge = edge;
    this.dist = dist;
  }

  /**
   * @return {@code true} if the edge is vertical, {@code false} if it is horizontal.
   */
  public boolean isVertical() {
    return vertical;
  }

  /**
   * @return The coordinates of the edge.
   */
  public Vec2i getEdge() {
    return edge;
  }

  /**
   * @return The unsigned distance between the point and the edge (in pixels).
   */
  public int getDist() {
    return dist;
  }
}