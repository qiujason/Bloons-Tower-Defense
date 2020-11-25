/**
 * @author Annshine
 * API for Towers
 */
package ooga.backend.API;

import ooga.backend.ConfigurationException;
import ooga.backend.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.UpgradeChoice;

public interface TowersAPI {
  /**
   * Purpose: Method should be used to return the tower type
   * @return a TowerType that indicates what kind of Tower the class is
   */
  TowerType getTowerType();

  /**
   * Purpose: Method should be used to get the shooting rest rate of tower
   * @return double - shootingRestRate of tower
   */
  double getShootingRestRate();

  /**
   * Purpose: Method should be used to get the radius of tower
   * @return double - radius of tower
   */
  double getRadius();

  /**
   * Purpose: Method should be used to get the shooting speed of tower
   * @return double - shooting speed of tower
   */
  double getShootingSpeed();

  /**
   * Purpose: Method should be used to check whether there is a bloon in range within bloonsCollection
   * @param bloonsCollection
   * @return a boolean of whether there is a bloon in range
   */
  boolean checkBalloonInRange(BloonsCollection bloonsCollection);

  /**
   * Purpose: Method should be used to update ifRestPeriod
   * @param update - new boolean to update ifRestPeriod
   */
  void updateIfRestPeriod(boolean update);

  /**
   * Purpose: Method should be used to get ifRestPeriod
   * @return boolean - ifRestPeriod
   */
  boolean isIfRestPeriod();

  /**
   * Purpose: Method should be used to create projectiles and add to projectilesCollection
   * @param bloonsCollection: the collection to check if there is bloon in range
   * @param projectilesCollection: the collection to add new created projectiles
   * @return the Bloon to target at
   * @throws ConfigurationException
   */
  Bloon shoot(BloonsCollection bloonsCollection, ProjectilesCollection projectilesCollection)
      throws ConfigurationException;

  /**
   * Purpose: Method should be used to distance between tower and target
   * @param target: GamePiece to calculate distance difference
   * @return double - to represent distance between tower and target
   */
  double getDistance(GamePiece target);

  /**
   * Purpose: Method should be used to get cost of upgrade depending on upgrade choice
   * @param choice: Upgrade choice to check the costs for
   * @return the cost of an upgrade
   */
  int getCostOfUpgrade(UpgradeChoice choice);

  /**
   * Purpose: Method should be used to check if an upgrade can be upgraded
   * @return boolean representing whether an upgrade is possible
   */
  boolean canPerformUpgrade();

  /**
   * Purpose: Method should be used to perform an upgrade with a chosen upgrade choice
   * @param choice - the choice of upgrade type
   */
  void performUpgrade(UpgradeChoice choice);

  /**
   * Purpose: Method should be used to set the projectile type for the tower
   * @param update: the projectile type to update to
   */
  void setProjectileType(ProjectileType update);

  /**
   * Purpose: Method should be used to return the projectile type shot from the tower
   * @return ProjectileType that the tower shoots
   */
  ProjectileType getProjectileType();

  /**
   * Purpose: Method should return the total upgrade costs for upgrades that have been used
   * @return integer - total upgrade costs
   */
  int getTotalUpgradeCost();

}