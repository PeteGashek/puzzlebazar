package com.puzzlebazar.shared.util;

public class SquareGridValidatorImpl implements SquareGridValidator {

  private final Has2DSize squareGrid;
  
  /**
   * Creates a validator attached to the specified {@link Has2DSize}.
   * 
   * @param squareGrid The attached square grid, which must implement {@link Has2DSize}.
   */
  public SquareGridValidatorImpl(Has2DSize squareGrid) {
    this.squareGrid = squareGrid;
  }

  @Override
  public boolean isValidCell( int x, int y ) {
    return 0 <= x && x < squareGrid.getWidth() &&
           0 <= y && y < squareGrid.getHeight();
  }

  @Override
  public boolean isValidCell(Vec2i cell) {
    return isValidCell(cell.x, cell.y);
  }

  @Override
  public boolean isValidVerticalEdge( int x, int y ) {
    return 0 <= x && x <= squareGrid.getWidth() &&
           0 <= y && y < squareGrid.getHeight();
  }
  
  @Override
  public boolean isValidVerticalEdge(Vec2i edge) {
    return isValidVerticalEdge(edge.x, edge.y);
  }

  @Override
  public boolean isValidHorizontalEdge( int x, int y ) {
    return 0 <= x && x < squareGrid.getWidth() &&
           0 <= y && y <= squareGrid.getHeight();
  }
  
  @Override
  public boolean isValidHorizontalEdge(Vec2i edge) {
    return isValidHorizontalEdge(edge.x, edge.y);
  }

  @Override
  public boolean isValidVertex( int x, int y ) {
    return 0 <= x && x <= squareGrid.getWidth() &&
           0 <= y && y <= squareGrid.getHeight();
  }
  
  @Override
  public boolean isValidVertex(Vec2i vertex) {
    return isValidVertex(vertex.x, vertex.y);
  }
  
}
