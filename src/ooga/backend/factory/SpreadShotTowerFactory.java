package ooga.backend.factory;

import java.lang.reflect.Constructor;
import ooga.backend.ConfigurationException;
import ooga.backend.towers.SpreadShotTower;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public class SpreadShotTowerFactory implements TowerFactory{

  private static String TOWER_PATH = "ooga.backend.towers.Tower.";

  @Override
  public Tower createTower(TowerType type, int xPosition, int yPosition) {
    try {
      Class<?> towerClass = Class.forName(TOWER_PATH + type.name());
      Constructor<?> towerConstructor = towerClass
          .getDeclaredConstructor(int.class, int.class, int.class);
      return (SpreadShotTower) towerConstructor.newInstance(xPosition, yPosition, type.getRadius());
    } catch (Exception e) {
      throw new ConfigurationException("No tower class found for selected type of tower.");
    }
  }
}
