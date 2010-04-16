/**
 * 
 */
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