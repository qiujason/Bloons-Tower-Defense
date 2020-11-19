package ooga.backend.layout;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.API.LayoutAPI;
import ooga.visualization.BloonsApplication;

public class Layout implements LayoutAPI {

  private final int width;
  private final int height;
  private final List<List<LayoutBlock>> layoutConfig;
  private final double blockSize;

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

  @Override
  public LayoutBlock getBlock(int row, int col) {
    return layoutConfig.get(row).get(col);
  }

  @Override
  public double getBlockDx(int row, int col) {
    return getBlock(row, col).getDx();
  }

  @Override
  public double getBlockDy(int row, int col) {
    return getBlock(row, col).getDy();
  }

  /**
   * Gets grid coordinates of start block
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

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean isEndOfPath(int row, int col) {
    return getBlock(row, col).isEndBlock();
  }

  public double getBlockSize() {
    return blockSize;
  }

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
