package ooga.controller;

import ooga.backend.towers.Tower;

public interface TowerMenuInterface {

  boolean buyTower(Tower tower);

  void sellTower(Tower tower);
}
