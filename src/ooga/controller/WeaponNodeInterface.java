package ooga.controller;

import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.ShootingChoice;
import ooga.backend.towers.TowerType;
import ooga.visualization.nodes.TowerNode;

public interface WeaponNodeInterface {

  void makeWeapon(TowerType towerType);

  void makeRoadWeapon(RoadItemType roadItemType);

  void removeWeapon(TowerNode towerNode);

  void upgradeRate(TowerNode towerNode);

  void upgradeRange(TowerNode towerNode);

  void setTargetingOption(TowerNode towerNode, ShootingChoice shootingChoice);

}
