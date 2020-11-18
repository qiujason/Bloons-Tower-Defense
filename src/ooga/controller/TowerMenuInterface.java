package ooga.controller;

import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.visualization.nodes.TowerNode;

public interface TowerMenuInterface {

  boolean buyTower(TowerType towerType, TowerNodeHandler towerNodeHandler);

  void sellTower(Tower tower, TowerNodeHandler towerNodeHandler);

  void upgradeRange(Tower tower, TowerNodeHandler towerNodeHandler);

  void upgradeRate(Tower tower, TowerNodeHandler towerNodeHandler);

  void setTargetingOption();

}
