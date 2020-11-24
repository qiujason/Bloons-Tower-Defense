package ooga.visualization.nodes;

import ooga.backend.towers.TowerType;

public class TowerNodeFactory implements WeaponNodeFactory {

  @Override
  public TowerNode createTowerNode(TowerType tower, double xPosition, double yPosition,
      double radius) {
    return new TowerNode(tower, xPosition, yPosition, radius);
  }

}
