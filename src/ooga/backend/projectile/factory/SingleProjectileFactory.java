/**
 * This class should be used to create a single projectiles
 * @author Annshine
 */
package ooga.backend.projectile.factory;

import java.lang.reflect.Constructor;
import ooga.backend.ConfigurationException;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;

public class SingleProjectileFactory implements ProjectileFactory {

  public static final String DART_PATH = "ooga.backend.projectile.types.";

  /**
   * Method should be used to create a dart with the specified parameters
   * @param type
   * @param xPosition
   * @param yPosition
   * @param xVelocity
   * @param yVelocity
   * @param angle
   * @return
   * @throws ConfigurationException
   */
  @Override
  public Projectile createDart(ProjectileType type, double xPosition, double yPosition,
      double xVelocity, double yVelocity, double angle) throws ConfigurationException {
    try {
      Class<?> dartClass = Class.forName(DART_PATH + type.toString());
      Constructor<?> dartConstructor = dartClass
          .getDeclaredConstructor(ProjectileType.class, double.class, double.class, double.class,
              double.class, double.class);
      return (Projectile) dartConstructor
          .newInstance(type, xPosition, yPosition, xVelocity, yVelocity, angle);
    } catch (Exception e) {
      throw new ConfigurationException("NoDartError");
    }
  }
}
