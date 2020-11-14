package ooga.visualization.nodes;

import ooga.backend.towers.TowerType;

public class TowerNodeFactory implements WeaponNodeFactory {

  @Override
  public TowerNode createTowerNode(TowerType towerType, double xPosition, double yPosition, double radius) {
    return new TowerNode(towerType, xPosition, yPosition, radius);
  }

}
