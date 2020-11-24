package ooga.backend.layout;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.API.LayoutAPI;
import ooga.visualization.BloonsApplication;

/**
 * This class represents the backend implementation of the game map used for Bloons Tower Defense.
 * It aggregates LayoutBlocks and organizes it into a grid, and contains methods used to access
 * specific aspects of the grid, i.e its height, width, specific blocks, etc
 */
public class Layout implements LayoutAPI {

  private final int width;
  private final int height;
  private final List<List<LayoutBlock>> layoutConfig;
  private final double blockSize;

  /**
   * Creates an instance of the Layout class
   * @param layoutConfig the configuration of the layout, which is read in from the LayoutReader
   */
  public Layout(List<List<String>> layoutConfig) {
    this.width = layoutConfig.get(0).size();
    this.height = layoutConfig.size();
    this.layoutConfig = createLayoutConfig(layoutConfig);
    this.blockSize = initializeBlockSize();
  }

  private List<List<LayoutBlock>> createLayoutConfig(List<List<String>> layoutConfig) {
    List<List<LayoutBlock>> layoutBlocks = new ArrayList<>();
    for (List<String> row : layoutConfig) {
      List<LayoutBlock> blockRow = new ArrayList<>();
      for (String blockType : row) {
        blockRow.add(new LayoutBlock(blockType));
      }
      layoutBlocks.add(blockRow);
    }
    return layoutBlocks;
  }

  private double initializeBlockSize() {
    double blockWidth = BloonsApplication.GAME_WIDTH / width;
    double blockHeight = BloonsApplication.GAME_HEIGHT / height;
    return Math.min(blockWidth, blockHeight);
  }

  /**
   * Returns a LayoutBlock based on the given row and column
   * @param row the row of the desired block
   * @param col the column of the desired block
   * @return LayoutBlock
   *
   * Part of the Layout API
   */
  @Override
  public LayoutBlock getBlock(int row, int col) {
    return layoutConfig.get(row).get(col);
  }

  /**
   * Returns the dx for the LayoutBlock at a given row and column
   * @param row the row of the desired block
   * @param col the col of the desired block
   * @return double representing the direction in the x-direction that the block should direct bloons
   *
   * Part of the Layout API
   */
  @Override
  public double getBlockDx(int row, int col) {
    return getBlock(row, col).getDx();
  }

  /**
   * Returns the dy for the LayoutBlock at a given row and column
   * @param row the row of the desired block
   * @param col the col of the desired block
   * @return double representing the direction in the y-direction that the block should direct bloons
   *
   * Part of the Layout API
   */
  @Override
  public double getBlockDy(int row, int col) {
    return getBlock(row, col).getDy();
  }

  /**
   * Gets grid coordinates of start block (where bloons spawn)
   *
   * @return in row, col format, or y,x format
   */
  public int[] getStartCoordinates() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (layoutConfig.get(i).get(j).getBlockType().equals("*")) {
          return new int[]{i, j};
        }
      }
    }
    return new int[2];
  }

  /**
   * Returns the width of the Layout grid
   * @return width
   *
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns the height of the Layout grid
   * @return height
   */
  public int getHeight() {
    return height;
  }

  public boolean isEndOfPath(int row, int col) {
    return getBlock(row, col).isEndBlock();
  }

  /**
   * Returns the block size of the layout
   * @return
   */
  public double getBlockSize() {
    return blockSize;
  }

  /**
   * Returns the start block in the Layout grid. This is where bloons first spawn.
   * @return LayoutBlock that is the starting block
   */
  public LayoutBlock getStartBlock() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (layoutConfig.get(i).get(j).getBlockType().equals("*")) {
          return layoutConfig.get(i).get(j);
        }
      }
    }
    return layoutConfig.get(0).get(0);
  }

}
