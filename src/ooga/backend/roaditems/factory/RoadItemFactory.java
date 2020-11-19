package ooga.backend.roaditems.factory;

import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;

public interface RoadItemFactory {
  RoadItem createRoadItem(RoadItemType type, double xPosition, double yPosition);
}
