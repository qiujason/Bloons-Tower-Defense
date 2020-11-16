package ooga.controller;

import ooga.backend.towers.TowerType;
import ooga.visualization.nodes.TowerNode;

public interface TowerMenuInterface {

  void buyTower(TowerType towerType);

  void selectTower(TowerNode tower);

  void closeMenu(TowerNode tower);

  void sellTower(TowerNode tower);

  void upgradeTower(TowerNode tower);

}
