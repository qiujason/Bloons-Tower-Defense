package ooga.controller;

import java.util.ResourceBundle;
import ooga.AlertHandler;
import ooga.backend.bank.Bank;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.UpgradeChoice;

public class TowerMenuController implements TowerMenuInterface {

  private Bank bank;
  private ResourceBundle inGameMessage = ResourceBundle.getBundle("InGameMessages");

  public TowerMenuController(Bank bank){
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
  public void sellTower(Tower tower) {
    bank.sellTower(tower);
  }

  @Override
  public void upgradeRange(Tower tower){
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
    if (bank.buyUpgrade(UpgradeChoice.ShootingRestRateUpgrade, tower)) {
      tower.performUpgrade(UpgradeChoice.ShootingRestRateUpgrade);
    }
    else {
      new AlertHandler(inGameMessage.getString("InsufficientFunds"),
          inGameMessage.getString("ForUpgradeRate"));    }
  }

}
