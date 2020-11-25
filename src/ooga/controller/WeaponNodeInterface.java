package ooga.controller;

import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.ShootingChoice;
import ooga.backend.towers.TowerType;
import ooga.visualization.nodes.TowerNode;

/**
 * Interface holding the methods that handle the functionality for all of the in game user inputs
 * related to weapons such as Towers and RoadItems.
 */
public interface WeaponNodeInterface {

  /**
   * Creates the Tower based on the TowerType that the Player selects.
   *
   * @param towerType the selected TowerType
   */
  void makeWeapon(TowerType towerType);

  /**
   * Creates the Road Item based on the RoadItemType that the Player selects.
   *
   * @param roadItemType the selected RoadItemType
   */
  void makeRoadWeapon(RoadItemType roadItemType);

  /**
   * Removes the selected TowerNode from the game.
   *
   * @param towerNode the selected TowerNode
   */
  void removeWeapon(TowerNode towerNode);

  /**
   * Upgrades the shooting rate of the selected TowerNode
   *
   * @param towerNode the selected TowerNode
   */
  void upgradeRate(TowerNode towerNode);

  /**
   * Upgrades the range of the selected TowerNode
   *
   * @param towerNode the selected TowerNode
   */
  void upgradeRange(TowerNode towerNode);

  /**
   * Changes the targeting priority of the selected TowerNode based on the selected ShootingChoice
   *
   * @param towerNode the selected TowerNode
   * @param shootingChoice the selected ShootingChoice
   */
  void setTargetingOption(TowerNode towerNode, ShootingChoice shootingChoice);

}
