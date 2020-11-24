package ooga.visualization.nodes;

import ooga.backend.roaditems.RoadItemType;

/**
 * This interface is used to create a front-end road item node.
 */
public interface ItemNodeFactory {

  /**
   * Creates a RoadItemNode based on its type, the given coordinates, and the radius.
   * @param roadItemType the type of RoadItem that is being made
   * @param xPosition the x position of the RoadItemNode
   * @param yPosition the y position of the RoadItemNode
   * @param radius the radius of the RoadItemNode
   * @return the created RoadItemNode
   */
  RoadItemNode createItemNode(RoadItemType roadItemType, double xPosition, double yPosition,
      double radius);

}