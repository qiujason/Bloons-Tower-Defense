/**
 * Class should be used to create towers
 * Implements TowerFactory interface
 * @author Annshine
 */
package ooga.backend.towers.factory;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;
import ooga.backend.ConfigurationException;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public class SingleTowerFactory implements TowerFactory {

  private final static String TOWER_PATH = "ooga.backend.towers.";

  /**
   * Method should be used to create a tower
   * @param type
   * @param xPosition
   * @param yPosition
   * @return created tower with correct parameters
   * @throws ConfigurationException
   */
  @Override
  public Tower createTower(TowerType type, double xPosition, double yPosition)
      throws ConfigurationException {
    double radius = type.getRadius();
    double shootingSpeed = type.getShootingSpeed();
    double shootingRestRate = type.getShootingRestRate();
    try {
      ResourceBundle bundle = ResourceBundle.getBundle("towers/" + type.name());
      if (bundle.containsKey("Radius")) {
        radius = Integer.parseInt(bundle.getString("Radius"));
      }
      if (bundle.containsKey("ShootingSpeed")) {
        shootingSpeed = Integer.parseInt(bundle.getString("ShootingSpeed"));
      }
      if (bundle.containsKey("ShootingRestRate")) {
        shootingRestRate = Integer.parseInt(bundle.getString("ShootingRestRate"));
      }
    } catch (Exception ignored) {
    }
    return constructTower(type, xPosition, yPosition, radius, shootingSpeed, shootingRestRate);
  }

  private Tower constructTower(TowerType type, double xPosition, double yPosition, double radius, double shootingSpeed, double shootingRestRate)
      throws ConfigurationException {
    try {
      Class<?> towerClass = Class.forName(TOWER_PATH + type.toString());
      Constructor<?> towerConstructor = towerClass
          .getDeclaredConstructor(double.class, double.class, double.class,
              double.class, double.class);
      return (Tower) towerConstructor.newInstance(xPosition, yPosition, radius, shootingSpeed,
          shootingRestRate);
    } catch (Exception e) {
      throw new ConfigurationException("NoTowerClass");
    }
  }
}
