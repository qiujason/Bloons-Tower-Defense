package ooga.controller;

import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

/**
 * Interface holding the methods that connect user inputs for Towers and RoadItems with the Bank.
 */
public interface WeaponBankInterface {

  /**
   * @param tower takes in the TowerType that is being purchased.
   * @return true if the purchase is successful and false if purchase cannot be made.
   */
  boolean buyTower(TowerType tower);

  /**
   * @param itemType takes in the RoadItemType that is being purchased.
   * @return true if the purchase is successful and false if purchase cannot be made.
   */
  boolean buyRoadItem(RoadItemType itemType);

  /**
   * @param tower takes in the Tower that is being sold.
   */
  void sellTower(Tower tower);

  /**
   * Purchases and performs the range upgrade.
   *
   * @param tower takes in the tower that is getting a range upgrade.
   */
  void upgradeRange(Tower tower);

  /**
   * Purchases and performs the shooting rate upgrade.
   *
   * @param tower takes in the tower that is getting a shooting rate upgrade.
   */
  void upgradeRate(Tower tower);
}
