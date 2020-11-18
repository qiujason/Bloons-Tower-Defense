package ooga.controller;

import ooga.backend.bank.Bank;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.TowersCollection;

public class TowerMenuController implements TowerMenuInterface {

  private TowersCollection towers;
  private Bank bank;

  public TowerMenuController(TowersCollection towers, Bank bank){
    this.towers = towers;
    this.bank = bank;
  }

  @Override
  public boolean buyTower(TowerType towerType) {
    return bank.buyTower(towerType);
  }

  @Override
  public void sellTower(Tower tower) {
    bank.sellTower(tower.getTowerType());
  }

}
