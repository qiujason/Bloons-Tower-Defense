package ooga.controller;

import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.visualization.nodes.TowerNode;

public interface TowerMenuInterface {

  void buyTower(TowerType towerType);

  void sellTower(Tower tower);

  void upgradeRange(Tower tower);

  void upgradeRate(Tower tower);

  void setTargetingOption();

}
