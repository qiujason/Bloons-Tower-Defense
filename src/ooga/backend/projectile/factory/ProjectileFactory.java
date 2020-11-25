/**
 * @author Annshine
 * Interface for ProjectileFactory to create projectiles
 */
package ooga.backend.projectile.factory;

import ooga.backend.ConfigurationException;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;

public interface ProjectileFactory {

  Projectile createDart(ProjectileType type, double xPosition, double yPosition, double xVelocity,
      double yVelocity, double angle)
      throws ConfigurationException;
}
