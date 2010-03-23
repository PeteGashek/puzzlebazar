package com.puzzlebazar.client.util;

/**
 * A utility class that can check the validity of cell, vertices and
 * edge coordinates within a {@link SquareGrid}.
 * 
 * @author Philippe Beaudoin
 */
public class SquareGridValidator {

  private final SquareGrid squareGrid;
  
  /**
   * Creates a validator attached to the specified {@link SquareGrid}.
   * 
   * @param squareGrid The {@link SquareGrid}.
   */
  public SquareGridValidator(SquareGrid squareGrid) {
    this.squareGrid = squareGrid;
  }
  
  /**
   * Checks if the passed coordinate is a valid cell coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param x The x cell coordinate.
   * @param y The y cell coordinate.
   * @return {@code true} if the passed coordinate is a valid cell coordinate, {@code false} otherwise.
s   */
  public boolean isValidCell( int x, int y ) {
    return 0 <= x && x < squareGrid.getWidth() &&
           0 <= y && y < squareGrid.getHeight();
  }

  /**
   * Checks if the passed coordinate is a valid cell coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param cell The cell coordinate.
   * @return {@code true} if the passed coordinate is a valid cell coordinate, {@code false} otherwise.
   */
  public boolean isValidCell(Vec2i cell) {
    return isValidCell(cell.x, cell.y);
  }

  /**
   * Checks if the passed coordinate is a valid vertical edge coordinate.
   * It must have a valid vertex x-coordinate and a valid cell y-coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param x The x vertical edge coordinate.
   * @param y The y vertical edge coordinate.
   * @return {@code true} if the passed coordinate is a valid vertical edge coordinate, {@code false} otherwise.
   */
  public boolean isValidVerticalEdge( int x, int y ) {
    return 0 <= x && x <= squareGrid.getWidth() &&
           0 <= y && y < squareGrid.getHeight();
  }
  
  /**
   * Checks if the passed coordinate is a valid vertical edge coordinate.
   * It must have a valid vertex x-coordinate and a valid cell y-coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param edge The vertical edge coordinate.
   * @return {@code true} if the passed coordinate is a valid vertical edge coordinate, {@code false} otherwise.
   */
  public boolean isValidVerticalEdge(Vec2i edge) {
    return isValidVerticalEdge(edge.x, edge.y);
  }

  /**
   * Checks if the passed coordinate is a valid horizontal edge coordinate.
   * It must have a valid cell x-coordinate and a valid vertex y-coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param x The x horizontal edge coordinate.
   * @param y The y horizontal edge coordinate.
   * @return {@code true} if the passed coordinate is a valid horizontal edge coordinate, {@code false} otherwise.
   */
  public boolean isValidHorizontalEdge( int x, int y ) {
    return 0 <= x && x < squareGrid.getWidth() &&
           0 <= y && y <= squareGrid.getHeight();
  }
  
  /**
   * Checks if the passed coordinate is a valid horizontal edge coordinate.
   * It must have a valid cell x-coordinate and a valid vertex y-coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param edge The horizontal edge coordinate.
   * @return {@code true} if the passed coordinate is a valid horizontal edge coordinate, {@code false} otherwise.
   */
  public boolean isValidHorizontalEdge(Vec2i edge) {
    return isValidHorizontalEdge(edge.x, edge.y);
  }

  /**
   * Checks if the passed coordinate is a valid vertex coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param x The x vertex coordinate.
   * @param y The y vertex coordinate.
   * @return {@code true} if the passed coordinate is a valid vertex coordinate, {@code false} otherwise.
   */
  public boolean isValidVertex( int x, int y ) {
    return 0 <= x && x <= squareGrid.getWidth() &&
           0 <= y && y <= squareGrid.getHeight();
  }
  
  /**
   * Checks if the passed coordinate is a valid vertex coordinate.
   * (See {@link Recti} for details on cell and vertex coordinates.) 
   * 
   * @param vertex The vertex coordinate.
   * @return {@code true} if the passed coordinate is a valid vertex coordinate, {@code false} otherwise.
   */
  public boolean isValidVertex(Vec2i vertex) {
    return isValidVertex(vertex.x, vertex.y);
  }
  
}
