package ooga.controller;

import java.util.ResourceBundle;
import ooga.AlertHandler;
import ooga.backend.bank.Bank;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.UpgradeChoice;

public class WeaponBankController implements WeaponBankInterface {

  private final Bank bank;
  private static final String NO_MONEY = "InsufficientFunds";
  private static final String TOWER = "ForTower";
  private static final String ROAD_ITEM = "ForRoadItem";
  private static final String RANGE_UPGRADE = "ForUpgradeRange";
  private static final String RATE_UPGRADE = "ForUpgradeRate";
  private static final ResourceBundle inGameMessage = ResourceBundle.getBundle("InGameMessages");

  public WeaponBankController(Bank bank){
    this.bank = bank;
  }

  @Override
  public boolean buyTower(TowerType tower) {
    if(bank.buyTower(tower)) {
      return true;
    } else{
      new AlertHandler(inGameMessage.getString(NO_MONEY),
          inGameMessage.getString(TOWER));
      return false;
    }
  }

  @Override
  public boolean buyRoadItem(RoadItemType itemType) {
    if(bank.buyRoadItem(itemType)){
      return bank.buyRoadItem(itemType);
    }else{
      new AlertHandler(inGameMessage.getString(NO_MONEY),
          inGameMessage.getString(ROAD_ITEM));
      return false;
    }
  }


  @Override
  public void sellTower(Tower tower) {
    bank.sellTower(tower);
  }

  @Override
  public void upgradeRange(Tower tower){
    if(!tower.canPerformUpgrade()){
      return;
    }
    if (bank.buyUpgrade(UpgradeChoice.RadiusUpgrade, tower)) {
      tower.performUpgrade(UpgradeChoice.RadiusUpgrade);
    }
    else {
      new AlertHandler(inGameMessage.getString(NO_MONEY),
          inGameMessage.getString(RANGE_UPGRADE));
    }
  }

  @Override
  public void upgradeRate(Tower tower){
    if(!tower.canPerformUpgrade()){
      return;
    }
    if (bank.buyUpgrade(UpgradeChoice.ShootingRestRateUpgrade, tower)) {
      tower.performUpgrade(UpgradeChoice.ShootingRestRateUpgrade);
    }
    else {
      new AlertHandler(inGameMessage.getString(NO_MONEY),
          inGameMessage.getString(RATE_UPGRADE));    }
  }

}
