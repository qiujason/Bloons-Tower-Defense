/**
 * Implementes the RoadItemFactory to create road items
 * @author Annshine
 */
package ooga.backend.roaditems.factory;

import java.lang.reflect.Constructor;
import ooga.backend.ConfigurationException;
import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;

public class SingleRoadItemFactory implements RoadItemFactory {

  private final static String ROADITEM_PATH = "ooga.backend.roaditems.types.";

  /**
   * Should be used to create road items
   * @param type
   * @param xPosition
   * @param yPosition
   * @return
   * @throws ConfigurationException
   */
  @Override
  public RoadItem createRoadItem(RoadItemType type, double xPosition, double yPosition)
      throws ConfigurationException {
    try {
      Class<?> towerClass = Class.forName(ROADITEM_PATH + type.toString());
      Constructor<?> towerConstructor = towerClass
          .getDeclaredConstructor(double.class, double.class);
      return (RoadItem) towerConstructor.newInstance(xPosition, yPosition);
    } catch (Exception e) {
      throw new ConfigurationException("NoRoadItemClass");
    }
  }
}
