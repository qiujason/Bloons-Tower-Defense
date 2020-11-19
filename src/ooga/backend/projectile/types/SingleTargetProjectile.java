package ooga.backend.projectile.types;

import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;

public class SingleTargetProjectile extends Projectile {

  public SingleTargetProjectile(ProjectileType type, double xPosition, double yPosition,
      double xVelocity, double yVelocity, double angle) {
    super(type, xPosition, yPosition, xVelocity, yVelocity, angle);
  }
}
