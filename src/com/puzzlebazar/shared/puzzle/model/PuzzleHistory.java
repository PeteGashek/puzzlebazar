package com.puzzlebazar.shared.puzzle.model;

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
 * <li>A user tagging or untagging the puzzle...</li>
 * </ul> 
 * 
 * @author Philippe Beaudoin
 */

import java.io.Serializable;

public interface PuzzleHistory extends Serializable {

}
