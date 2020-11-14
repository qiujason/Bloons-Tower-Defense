package ooga.backend.towers;

public enum TowerType {
  SingleProjectileShooter(100, 5, 15, true),
  MultiProjectileShooter(5, 10, 10, false),
  SpreadProjectileShooter(15, 20, 20, true),
  UnlimitedRangeProjectileShooter(800, 5, 10, true),
  SuperSpeedProjectileShooter(600, 1, 30, true),
  FrozenSpreadShooter(15, 10, 20, false);

  private double radius;
  private double shootingRestRate;
  private double shootingSpeed;
  private boolean ifSingleShot;

  TowerType(int radius) {
    this.radius = radius;
  }

  TowerType(int radius, int shootingRestRate) {
    this.radius = radius;
    this.shootingRestRate = shootingRestRate;
  }

  TowerType(int radius, int shootingRestRate, int shootingSpeed) {
    this.radius = radius;
    this.shootingRestRate = shootingRestRate;
    this.shootingSpeed = shootingSpeed;
  }

  TowerType(int radius, int shootingRestRate, int shootingSpeed, boolean ifSingleShot) {
    this.radius = radius;
    this.shootingRestRate = shootingRestRate;
    this.shootingSpeed = shootingSpeed;
    this.ifSingleShot = ifSingleShot;
  }

  public double getRadius() {
    return radius;
  }
  public double getShootingRestRate(){
    return shootingRestRate;
  }
  public double getShootingSpeed(){
    return shootingSpeed;
  }


  @Override
  public String toString() {
    if(ifSingleShot){
      return "singleshottowers." + super.toString();
    } else{
      return "spreadshottowers." + super.toString();
    }
  }
}
