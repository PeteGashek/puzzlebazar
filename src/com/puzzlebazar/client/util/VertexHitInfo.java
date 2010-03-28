/**
 * 
 */
package com.puzzlebazar.client.util;

import com.puzzlebazar.shared.util.Vec2i;


/**
 * A class containing information to locate a point relative to an vertex
 * in a square grid.
 * 
 * @author Philippe Beaudoin
 */
public class VertexHitInfo {

  private final Vec2i vertex;  
  private final Vec2i dist;

  /**
   * Creates information about an invalid vertex.
   */
  public VertexHitInfo(Vec2i vertex, Vec2i dist) {
    this.vertex = vertex;
    this.dist = dist;
  }

  /**
   * @return The vextex coordinates.
   */
  public Vec2i getVertex() {
    return vertex;
  }

  /**
   * @return The unsigned distance in x and y between the hit point and the vertex (in pixels).
   */
  public Vec2i getDist() {
    return dist;
  }

}