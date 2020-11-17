package ooga.backend.projectile;

public class SingleTargetProjectile extends Projectile {

  public SingleTargetProjectile(ProjectileType type, double xPosition, double yPosition,
      double xVelocity, double yVelocity, double angle) {
    super(type, xPosition, yPosition, xVelocity, yVelocity, angle);
  }
}
