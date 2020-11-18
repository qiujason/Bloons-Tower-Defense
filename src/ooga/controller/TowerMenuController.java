package ooga.controller;

import ooga.backend.bank.Bank;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public class TowerMenuController implements TowerMenuInterface {

  private Bank bank;

  public TowerMenuController(Bank bank){
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

}
