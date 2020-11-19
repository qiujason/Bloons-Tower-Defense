package ooga.backend.roaditems.factory;

import ooga.backend.ConfigurationException;
import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;

public interface RoadItemFactory {

  RoadItem createRoadItem(RoadItemType type, double xPosition, double yPosition)
      throws ConfigurationException;
}
