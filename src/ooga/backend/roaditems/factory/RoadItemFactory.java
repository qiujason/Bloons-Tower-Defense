/**
 * Interface for RoadItemsFactory implemented to create road items
 * @author Annshine
 */
package ooga.backend.roaditems.factory;

import ooga.backend.ConfigurationException;
import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;

public interface RoadItemFactory {

  /**
   * Should be used to create road items
   * @param type
   * @param xPosition
   * @param yPosition
   * @return
   * @throws ConfigurationException
   */
  RoadItem createRoadItem(RoadItemType type, double xPosition, double yPosition)
      throws ConfigurationException;
}
