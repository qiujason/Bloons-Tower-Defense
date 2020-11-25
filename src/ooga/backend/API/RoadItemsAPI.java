/**
 * @author Annshine
 * API for Road Items
 */
package ooga.backend.API;

import ooga.backend.roaditems.RoadItemType;

public interface RoadItemsAPI {

  /**
   * Purpose: Method should be used to return whether a road item have expired / or should be removed from screen
   * @return boolean indicating whether a road item should be removed
   */
  boolean shouldRemove();

  /**
   * Purpose: Return the type of road item it is
   * @return a RoadItemType to indicate what kind of road item the class is
   */
  RoadItemType getType();
}
