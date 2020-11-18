package ooga.controller;

import ooga.backend.bank.Bank;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.UpgradeChoice;

public class WeaponBankController implements WeaponBankInterface {

  private Bank bank;

  public WeaponBankController(Bank bank){
    this.bank = bank;
  }

  @Override
  public boolean buyTower(TowerType tower) {
    return bank.buyTower(tower);
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
      System.out.println("make more money cuh");
    }
  }

  @Override
  public void upgradeRate(Tower tower){
    if (bank.buyUpgrade(UpgradeChoice.ShootingRestRateUpgrade, tower)) {
      tower.performUpgrade(UpgradeChoice.ShootingRestRateUpgrade);
    }
    else {
      System.out.println("make more money cuh");
    }
  }

}
