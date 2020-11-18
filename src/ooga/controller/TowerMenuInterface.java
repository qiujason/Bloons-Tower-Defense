package ooga.controller;

import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public interface TowerMenuInterface {

  boolean buyTower(TowerType tower);

  void sellTower(Tower tower);
}
