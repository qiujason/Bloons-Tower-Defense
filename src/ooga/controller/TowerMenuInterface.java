package ooga.controller;

import ooga.backend.towers.TowerType;

public interface TowerMenuInterface {

  void buyTower(TowerType towerType);

  void selectTower();

  void sellTower();

  void upgradeTower();

}
