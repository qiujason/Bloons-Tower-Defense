package ooga.backend.factory;

import java.lang.reflect.Constructor;
import ooga.backend.ConfigurationException;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public class SingleShotTowerFactory implements TowerFactory {

  private static String TOWER_PATH = "ooga.backend.towers.Tower.";

  @Override
  public Tower createTower(TowerType type, int xPosition, int yPosition) {
    try {
      Class<?> towerClass = Class.forName(TOWER_PATH + type.name());
      Constructor<?> cellConstructor = towerClass
          .getDeclaredConstructor(int.class, int.class, int.class);
      return (Tower) cellConstructor.newInstance(xPosition, yPosition, type.getRadius());
    } catch (Exception e) {
      throw new ConfigurationException("No tower class found for selected type of tower.");
    }
  }
}
