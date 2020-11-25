/**
 * @author Annshine
 * API should be used to represent a bank
 */
package ooga.backend.API;

import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public interface BankAPI {

  /**
   * Purpose: Method should be used to add all the round bonuses up to a particular level
   * @param level
   */
  void advanceToLevel(int level);

  /**
   * Purpose: Method should be used to add the round bonus of the current level
   */
  void advanceOneLevel();

  /**
   * Purpose: Method should be used return the current money within the bank
   */
  int getCurrentMoney();

  /**
   * Purpose: Method should be used to buy a certain kind of tower and decrease the current money
   * @param buyTower: a TowerType enum that indicates what kind of tower is being bought
   * @return a boolean indicating whether a tower can be bought based on the current value in bank and how much a tower costs
   */
  boolean buyTower(TowerType buyTower);

  /**
   * Purpose: Method should be used to sell a particular tower and add to the current money
   * @param sellTower: The tower that should be sold
   */
  void sellTower(Tower sellTower);

  /**
   * Purpose: Method should be used to add 1 to the current money to reflect one dollar being added for each bloon pop
   */
  void addPoppedBloonValue();

}
