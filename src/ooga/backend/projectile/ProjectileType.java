package ooga.backend.projectile;

public enum ProjectileType {
  SingleTargetProjectile(1),
  SpreadProjectile(3),
  FreezeTargetProjectile(1),
  CamoTargetProjectile(1);

  private final double radius;

  ProjectileType(double radius) {
    this.radius = radius;
  }

  public double getRadius() {
    return radius;
  }
}
