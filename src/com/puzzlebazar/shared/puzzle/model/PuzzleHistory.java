package com.puzzlebazar.shared.puzzle.model;

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

import java.io.Serializable;

/**
 * List of all high-level actions executed on a puzzle.
 * This is not a list of low-level moves executed
 * when solving a puzzle: it only contains high-level
 * actions such as:
 * <ul>
 * <li>Creating a puzzle ;</li>
 * <li>Changing a puzzle {@link PrivacySettings} ;</li>
 * <li>Adding a {@link Comment} on a puzzle ;</li>
 * <li>Adding a {@link PuzzleSolve} to the puzzle ;</li>
 * <li>A user tagging or untagging the puzzle ;</li>
 * <li>etc.</li>
 * </ul> 
 * 
 * @author Philippe Beaudoin
 */
public interface PuzzleHistory extends Serializable {

}
