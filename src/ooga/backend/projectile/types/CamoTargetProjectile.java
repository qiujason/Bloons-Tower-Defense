/**
 * CamoTargetProjectile should be able to hit Camo Bloons as well as all other bloons
 * @author Annshine
 */
package ooga.backend.projectile.types;

import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;

public class CamoTargetProjectile extends Projectile {

  public CamoTargetProjectile(ProjectileType type, double xPosition, double yPosition,
      double xVelocity, double yVelocity, double angle) {
    super(type, xPosition, yPosition, xVelocity, yVelocity, angle);
  }

}
