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
  public boolean buyTower(TowerType towerType, TowerNodeHandler towerNodeHandler) {
    Boolean bought = bank.buyTower(towerType);
    if (bought && canMakeTower()){
      towerNodeHandler.makeWeaponNode(towerType);
    }
    return (bought && canMakeTower());
  }

  @Override
  public void sellTower(Tower tower, TowerNodeHandler towerNodeHandler) {
    bank.sellTower(tower.getTowerType());
    towers.remove(tower);
  }

  @Override
  public void upgradeRange(Tower tower, TowerNodeHandler towerNodeHandler){
    tower.upgradeRadius();
  }

  @Override
  public void upgradeRate(Tower tower, TowerNodeHandler towerNodeHandler) {
    tower.upgradeShootingRestRate();
  }

  @Override
  public void setTargetingOption(){
  }

  private Boolean canMakeTower() {
    Boolean canMake = true;
    GamePieceIterator<Tower> iterator = towers.createIterator();
    while (iterator.hasNext()) {
      Tower checkTower = iterator.next();
      if (!checkTower.checkIfPlaced()) {
        canMake = false;
      }
    }
    return canMake;
  }
}
