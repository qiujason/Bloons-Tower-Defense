package ooga.visualization.nodes;

import ooga.backend.roaditems.RoadItemType;

/**
 * RoadItemNodeFactory that implements in the ItemNodeFactory interface. It creates the front
 * end RoadItemNode
 */
public class RoadItemNodeFactory implements ItemNodeFactory {

  /**
   * @param roadItemType the type of RoadItem that is being made
   * @param xPosition the x position of the RoadItemNode
   * @param yPosition the y position of the RoadItemNode
   * @param radius the radius of the RoadItemNode
   * @return the created front end RoadItemNode
   */
  @Override
  public RoadItemNode createItemNode(RoadItemType roadItemType, double xPosition, double yPosition,
      double radius) {
    return new RoadItemNode(roadItemType, xPosition, yPosition, radius);
  }
}
