package ooga.controller;

import ooga.backend.bank.Bank;
import ooga.backend.bloons.Bloon;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.TowersCollection;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;

public class TowerMenuController implements TowerMenuInterface {

  private TowersCollection towers;
  private Bank bank;
  private static final double towerDefaultPosition = -1;

  public TowerMenuController(TowersCollection towers, Bank bank){
    this.towers = towers;
    this.bank = bank;
  }

  @Override
  public void buyTower(TowerType towerType) {
    Boolean bought = bank.buyTower(towerType);
    if(bought) {
      if (canMakeTower()) {
        TowerFactory towerFactory = new SingleTowerFactory();
        Tower tower = towerFactory
            .createTower(towerType, towerDefaultPosition, towerDefaultPosition);
        towers.add(tower);
      }
    }
    else{
      System.out.println("make more money cuh");
    }
  }

  @Override
  public void sellTower(Tower tower) {
    bank.sellTower(tower.getTowerType());
    towers.remove(tower);
  }

  @Override
  public void upgradeRange(Tower tower){
    tower.upgradeRadius();
  }

  @Override
  public void upgradeRate(Tower tower) {
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
