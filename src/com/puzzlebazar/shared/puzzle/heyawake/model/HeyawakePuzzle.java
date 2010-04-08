package com.puzzlebazar.shared.puzzle.heyawake.model;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.philbeaudoin.gwtp.mvp.client.HandlerContainerImpl;
import com.puzzlebazar.shared.model.HasKey;
import com.puzzlebazar.shared.puzzle.squaregrid.model.CellArray;
import com.puzzlebazar.shared.util.Has2DSize;
import com.puzzlebazar.shared.util.KeyNotFoundException;
import com.puzzlebazar.shared.util.PuzzleMessage;
import com.puzzlebazar.shared.util.Recti;
import com.puzzlebazar.shared.util.Vec2i;

@PersistenceCapable
public class HeyawakePuzzle extends HandlerContainerImpl
implements HasKey, Has2DSize, CellArray<HeyawakeCellState>, Serializable {

  private static final long serialVersionUID = -7747407683017419004L;

  private final static HeyawakeCellState defaultState = new HeyawakeCellState( HeyawakeCellState.UNKNOWN );
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
  private String key;

  @Persistent
  @Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
  private Long keyId;
  
  @Persistent(dependent = "true")
  private HeyawakePuzzleInfo puzzleInfo;
  
  // Not using abstract Map class because of GWT serialization mechanism. 
  // See {@link http://www.gwtapps.com/doc/html/com.google.gwt.doc.DeveloperGuide.RemoteProcedureCalls.SerializableTypes.html}
  // for details.
  @Persistent(serialized="true", defaultFetchGroup = "true")
  private ArrayList<HeyawakeRoom> rooms;
  
  @Persistent(serialized="true", defaultFetchGroup = "true")
  private HeyawakeCellState[][] stateArray;
  
  @SuppressWarnings("unused")
  private HeyawakePuzzle() {
    // For serialization only
    super(false);
  }
  
  public HeyawakePuzzle( HeyawakePuzzleInfo puzzleInfo ) {
    super(false);
    this.puzzleInfo = puzzleInfo;
    rooms = new ArrayList<HeyawakeRoom>();
    stateArray = new HeyawakeCellState[getWidth()][getHeight()];
  }
  
  @Override
  public String getKey() {
    return key;
  }
  
  @Override
  public long getId() {
    return keyId;
  }

  @Override
  public int getHeight() {
    return puzzleInfo.getHeight();
  }

  @Override
  public int getWidth() {
    return puzzleInfo.getWidth();
  }  

  @Override
  public void clearCellStates() {
    for( int i=0; i<getWidth(); ++i )
      for( int j=0; j<getWidth(); ++j )
        stateArray[i][j] = null;
    
  }

  @Override
  public HeyawakeCellState getCellState(Vec2i loc) {
    HeyawakeCellState result = stateArray[loc.x][loc.y];
    if( result == null )
      return defaultState;
    return result;
  }

  @Override
  public void setCellState(Vec2i loc, HeyawakeCellState state) {
    stateArray[loc.x][loc.y] = state;
  }

  /**
   * Ensures that no room overlap within the heyawake puzzle. This
   * does not verify that the current state is legal, for this
   * call {@link #checkSolved()}.
   * 
   * @return {@code true} if the puzzle integrity is verified, that is, no room overlap. {@code false} otherwise. 
   */
  public boolean checkIntegrity() {
    int roomsPerCell[][] = countRoomsPerCell();
    for( int x=0; x<getWidth(); ++x )
      for( int y=0; y<getHeight(); ++y )
        if( roomsPerCell[x][y] > 1 )
          return false;
    return true;
  }

  /**
   * Ensures that this heyawake puzzle is legally solved.
   * 
   * @return {@code null} if all the cells of the desired type form a connected group, otherwise return a {@link List} of cell
   *         locations that form a disconnected group.
   */
  public PuzzleMessage checkSolved() {
    
    PuzzleMessage result = null;
    int roomsPerCell[][] = countRoomsPerCell();
    for( int x=0; x<getWidth(); ++x )
      for( int y=0; y<getHeight(); ++y )
        if( roomsPerCell[x][y] != 1 ) {
          if( result == null )
            result = new PuzzleMessage(true, "Some cells are covered by more than one room.");
          result.addErrorLocation( new Vec2i(x,y) );
        }
    
    if( result != null )
      return result;
    
    return new PuzzleMessage(false);
  }
  
  /**
   * Ensures that the {@link HeyawakeRoom} can be added to the puzzle.
   * This is only possible if the room does not overlap any existing
   * room.
   * 
   * @param room The {@link HeyawakeRoom} to add to the puzzle.
   * @return {@code true} if the room can be added, {@code false} otherwise.
   */
  public boolean canAddRoom(HeyawakeRoom room) {
    Recti rect = room.getRoomRect();
    for( HeyawakeRoom actualRoom : rooms ) 
      if( actualRoom.getRoomRect().cellsOverlap( rect ) )
        return false;
    return true;
  }
  
  /**
   * Adds a new room to the heyawake puzzle.
   * This version does not ensure that the move is valid,
   * for validation call {@link #canAddRoom(HeyawakeRoom)}
   * prior to calling this.
   * 
   * @param room Information on ths {@link HeyawakeRoom} to add.
   */
  public void addRoom( HeyawakeRoom room ) {
    rooms.add( room );
  }
 
  /**
   * Deletes a room from the heyawake puzzle.
   * 
   * @param cell00 The location of the top-left cell of the room to remove, a {@link Vec2i}. 
   * @throws KeyNotFoundException Thrown if the location was not found and the room was not removed.
   */
  public void deleteRoom( Vec2i cell00 ) throws KeyNotFoundException {
    int indexToRemove = 0;
    for( HeyawakeRoom room : rooms ) {
      if( room.getRoomRect().getCell00().equals( cell00 ) )
        break;
      indexToRemove++;
    }
    if( indexToRemove == rooms.size() )
      throw new KeyNotFoundException( "The room at postion " + cell00 + " was not found." );
    rooms.remove(indexToRemove);
  }

  /**
   * Counts the number of rooms on each cell. The integrity of a puzzle is verified if this
   * count is 0 or 1 for every cell. The puzzle is complete if this count is 1 for every cell.
   * 
   * @return An array of integer of size {@link #getWidth} times {@link #getHeight()} containing
   *         the number of rooms overlapping each cell.  
   */
  private int[][] countRoomsPerCell() {
    int[][] result = new int[getWidth()][getHeight()];
    for( int x=0; x<getWidth(); ++x)
      Arrays.fill(result[x], 0);
    for( HeyawakeRoom room : rooms ) {
      Recti rect = room.getRoomRect();
      for( int x=rect.x; x<rect.x+rect.w; ++x )
        for( int y=rect.y; y<rect.y+rect.h; ++y )
          result[x][y]++;
    }
    return result;
  }

}
