package ooga.backend.projectile;

public enum ProjectileType {
  SingleTargetProjectile(0),
  SpreadProjectile(10),
  FreezeTargetProjectile(0),
  CamoTargetProjectile(0);

  double radius;
  ProjectileType(double radius){
    this.radius = radius;
  }

  public double getRadius(){
    return radius;
  }
}
