package ooga.backend.layout;

import java.util.HashMap;
import java.util.Map;

/**
 * This class stores the information for each individual square of the game map as a "block". It
 * translates the block type, which is read in from the LayoutReader, into information that is
 * associated with that block type.
 */

public class LayoutBlock {

  private final String blockType;
  private double dx;
  private double dy;
  private final Map<String, double[]> velocityMap;


  /**
   * Creates an instanace of the LayoutBlock class
   * @param blockType the block type of the created LayoutBlock
   */
  public LayoutBlock(String blockType) {
    this.blockType = blockType;
    velocityMap = new HashMap<>();
    initializeVelocityMap();
    setDxDy(blockType);
  }

  private void initializeVelocityMap() { // pathing characters in properties file
    velocityMap.put(">", new double[]{1, 0});
    velocityMap.put("<", new double[]{-1, 0});
    velocityMap.put("v", new double[]{0, 1});
    velocityMap.put("^", new double[]{0, -1});
    velocityMap.put("*", new double[]{1, 0});
    velocityMap.put("@", new double[]{1, 0});
  }

  private void setDxDy(String blockType) {
    if (!blockType.equals("0")) {
      this.dx = velocityMap.get(blockType)[0];
      this.dy = velocityMap.get(blockType)[1];
    }
  }


  /**
   * Returns the direction in the x-direction that the block directs bloons
   * @return a double, either -1, 0, or 1, symbolizing left, no horizontal movement, or right, respectively
   *
   * Part of the LayoutBlock API
   */
  public double getDx() {
    return dx;
  }

  /**
   * Returns the direction in the y-direction that the block directs bloons
   * @return a double, either -1, 0, or 1, symbolizing up, no vertical movement, or down, respectively
   *
   * Part of the LayoutBlock API
   */
  public double getDy() {
    return dy;
  }

  /**
   * Returns the block type of the LayoutBlock
   * @return String representing the block type
   */
  public String getBlockType() {
    return blockType;
  }

  /**
   * Returns true if the block is the end block, where bloons exit the level.
   * @return true if the block is the end block.
   */
  public boolean isEndBlock() {
    return blockType.equals("@");
  }
}
