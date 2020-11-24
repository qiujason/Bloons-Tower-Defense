package ooga.visualization.nodes;

import ooga.backend.roaditems.RoadItemType;

public interface ItemNodeFactory {

  RoadItemNode createItemNode(RoadItemType roadItemType, double xPosition, double yPosition,
      double radius);

}