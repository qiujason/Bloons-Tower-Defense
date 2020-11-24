package ooga.controller;

import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public interface WeaponBankInterface {

  boolean buyTower(TowerType tower);

  boolean buyRoadItem(RoadItemType itemType);

  void sellTower(Tower tower);

  void upgradeRange(Tower tower);

  void upgradeRate(Tower tower);
}
