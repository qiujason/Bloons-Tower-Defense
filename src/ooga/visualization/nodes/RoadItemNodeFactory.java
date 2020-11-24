package ooga.visualization.nodes;

import ooga.backend.roaditems.RoadItemType;

public class RoadItemNodeFactory implements ItemNodeFactory {

  @Override
  public RoadItemNode createItemNode(RoadItemType roadItemType, double xPosition, double yPosition,
      double radius) {
    return new RoadItemNode(roadItemType, xPosition, yPosition, radius);
  }
}
