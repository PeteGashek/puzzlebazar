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
 * The simple interface of any object that has a discrete size.
 *
 * @author Philippe Beaudoin
 */
public interface Has2DSize {

  int UNKNOWN_SIZE = -1;

  /**
   * Access the number of cells in the horizontal direction.
   *
   * @return The width, that is, the number of cells in the horizontal direction.
   */
  int getWidth();

  /**
   * Access the number of cells in the vertical direction.
   *
   * @return The height, that is, the number of cells in the vertical direction.
   */
  int getHeight();

}
