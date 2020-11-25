/**
 * Interface Factory Design for to create Towers
 * @author Annshine
 */
package ooga.backend.towers.factory;

import ooga.backend.ConfigurationException;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public interface TowerFactory {

  /**
   * Method should be used to create a tower
   * @param type
   * @param xPosition
   * @param yPosition
   * @return created tower with correct parameters
   * @throws ConfigurationException
   */
  Tower createTower(TowerType type, double xPosition, double yPosition)
      throws ConfigurationException;
}
