package ooga.visualization.nodes;

import ooga.backend.towers.TowerType;

public interface WeaponNodeFactory {

  TowerNode createTowerNode(TowerType tower, double xPosition, double yPosition, double radius);

}