package ooga.backend.towers.factory;

import java.lang.reflect.Constructor;
import ooga.backend.ConfigurationException;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public class SingleTowerFactory implements TowerFactory {

  private static String TOWER_PATH = "ooga.backend.towers.";

  @Override
  public Tower createTower(TowerType type, double xPosition, double yPosition) {
    try {
      Class<?> towerClass = Class.forName(TOWER_PATH + type.toString());
      Constructor<?> towerConstructor = towerClass
          .getDeclaredConstructor(double.class, double.class, int.class);
      return (Tower) towerConstructor.newInstance(xPosition, yPosition, type.getRadius());
    } catch (Exception e) {
      throw new ConfigurationException("No tower class found for selected type of tower.");
    }
  }
}
