package ooga.controller;

import java.util.ResourceBundle;
import ooga.AlertHandler;
import ooga.backend.bank.Bank;
import ooga.backend.gameengine.GameMode;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.UpgradeChoice;

public class WeaponBankController implements WeaponBankInterface {

  private Bank bank;
  private ResourceBundle inGameMessage = ResourceBundle.getBundle("InGameMessages");

  public WeaponBankController(Bank bank){
    this.bank = bank;
  }

  @Override
  public boolean buyTower(TowerType tower) {
    if(bank.buyTower(tower)) {
      return true;
    } else{
      new AlertHandler(inGameMessage.getString("InsufficientFunds"),
          inGameMessage.getString("ForTower"));
      return false;
    }
  }

  @Override
  public boolean buyRoadItem(RoadItemType itemType) {
    return bank.buyRoadItem(itemType);
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
      new AlertHandler(inGameMessage.getString("InsufficientFunds"),
          inGameMessage.getString("ForUpgradeRange"));
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
      new AlertHandler(inGameMessage.getString("InsufficientFunds"),
          inGameMessage.getString("ForUpgradeRate"));    }
  }

}
