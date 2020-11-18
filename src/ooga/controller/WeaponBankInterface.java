package ooga.controller;

import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public interface WeaponBankInterface {

  boolean buyTower(TowerType tower);

  void sellTower(Tower tower);

  void upgradeRange(Tower tower);

  void upgradeRate(Tower tower);
}
