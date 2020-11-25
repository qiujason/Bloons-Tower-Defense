package ooga.controller;

import java.util.ResourceBundle;
import ooga.AlertHandler;
import ooga.backend.bank.Bank;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.UpgradeChoice;

/**
 * This class implements the WeaponBankInterface and contains all of the methods connecting
 * all weapon purchases, upgrades, and sale to the Bank.
 */
public class WeaponBankController implements WeaponBankInterface {

  private final Bank bank;
  private static final String NO_MONEY = "InsufficientFunds";
  private static final String TOWER = "ForTower";
  private static final String ROAD_ITEM = "ForRoadItem";
  private static final String RANGE_UPGRADE = "ForUpgradeRange";
  private static final String RATE_UPGRADE = "ForUpgradeRate";
  private static final ResourceBundle inGameMessage = ResourceBundle.getBundle("InGameMessages");

  /**
   * Constructor for the class where it initializes the Bank.
   *
   * @param bank takes in an instance of the Bank
   */
  public WeaponBankController(Bank bank) {
    this.bank = bank;
  }

  /**
   * Takes in a TowerType that has been bought in game and uses the Bank to purchase according to
   * the tower's value. Returns true if the purchase has been made. If there's not enough money,
   * the method returns false and throws an alert.
   *
   * @param tower takes in the TowerType that has been purchased
   * @return true if the purchase happens and false if there's not enough money
   */
  @Override
  public boolean buyTower(TowerType tower) {
    if (bank.buyTower(tower)) {
      return true;
    } else {
      new AlertHandler(inGameMessage.getString(NO_MONEY),
          inGameMessage.getString(TOWER));
      return false;
    }
  }

  /**
   * Takes in a RoadItemType that has been bought in game and uses the Bank to purchase according to
   * the road item's value. Returns true if the purchase has been made. If there's not enough money,
   * the method returns false and throws an alert.
   *
   * @param itemType takes in the RoadItemType that has been purchased
   * @return true if the purchase happens and false if there's not enough money
   */
  @Override
  public boolean buyRoadItem(RoadItemType itemType) {
    if (bank.buyRoadItem(itemType)) {
      return bank.buyRoadItem(itemType);
    } else {
      new AlertHandler(inGameMessage.getString(NO_MONEY),
          inGameMessage.getString(ROAD_ITEM));
      return false;
    }
  }

  /**
   * Takes in a Tower that is being sold and sells it using the Bank.
   *
   * @param tower the Tower that is being sold.
   */
  @Override
  public void sellTower(Tower tower) {
    bank.sellTower(tower);
  }

  /**
   * Takes in the Tower that is getting a range upgrade then purchases and performs the range upgrade.
   *
   * @param tower the Tower that is being upgraded
   */
  @Override
  public void upgradeRange(Tower tower) {
    if (!tower.canPerformUpgrade()) {
      return;
    }
    if (bank.buyUpgrade(UpgradeChoice.RadiusUpgrade, tower)) {
      tower.performUpgrade(UpgradeChoice.RadiusUpgrade);
    } else {
      new AlertHandler(inGameMessage.getString(NO_MONEY),
          inGameMessage.getString(RANGE_UPGRADE));
    }
  }

  /**
   * Takes in the Tower that is getting a shooting rate upgrade then purchases and performs the
   * shooting rate upgrade.
   *
   * @param tower the Tower that is being upgraded
   */
  @Override
  public void upgradeRate(Tower tower) {
    if (!tower.canPerformUpgrade()) {
      return;
    }
    if (bank.buyUpgrade(UpgradeChoice.ShootingRestRateUpgrade, tower)) {
      tower.performUpgrade(UpgradeChoice.ShootingRestRateUpgrade);
    } else {
      new AlertHandler(inGameMessage.getString(NO_MONEY),
          inGameMessage.getString(RATE_UPGRADE));
    }
  }

}
