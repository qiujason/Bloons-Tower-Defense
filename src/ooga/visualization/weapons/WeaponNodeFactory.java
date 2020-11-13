package ooga.visualization.weapons;

import ooga.backend.towers.TowerType;

public interface WeaponNodeFactory {

  TowerNode createTowerNode(TowerType towerType, double xPosition, double yPosition, double radius);

}