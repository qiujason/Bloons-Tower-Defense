package ooga.backend.towers;

public enum TowerType {
  DartTower(10),
  TackShooter(5),
  IceTower(5),
  SniperTower(300);

  private int radius;

  TowerType(int radius) {
    this.radius = radius;
  }

  public int getRadius() {
    return radius;
  }
}
