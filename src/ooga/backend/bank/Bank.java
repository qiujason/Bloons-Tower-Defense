package ooga.backend.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.backend.API.BankAPI;
import ooga.backend.gameengine.GameMode;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.UpgradeChoice;

/**
 * Bank allocates the round bonuses received after each round and can be determined in 3 ways: 1.
 * Providing a list of round bonuses 2. Providing a total number of rounds and using 100 as the
 * default starting round bonus 3. Providing a total number of rounds and the default starting round
 * bonus
 * <p>
 */

public class Bank implements BankAPI {

  public static final int STARTING_ROUND_BONUS = 100;
  public static final int STARTING_MONEY = 1000;

  private int currentMoney = STARTING_MONEY;
  private int currentLevel = 0;
  public static int STARTING_SANDBOX_MONEY = 10000;
  private GameMode gameMode;
  private List<Integer> roundBonus;
  private Map<TowerType, Integer> towerBuyMap;
  private Map<TowerType, Integer> towerSellMap;
  private Map<RoadItemType,Integer> roadItemBuyMap;

  // Provided list of round bonuses read from csv
  public Bank(Map<TowerType, Integer> towerBuyMap, Map<TowerType, Integer> towerSellMap,
      Map<RoadItemType,Integer> roadItemBuyMap,List<String> roundBonus, GameMode gameMode) {
    this.gameMode = gameMode;
    this.currentMoney = determineStartingMoney();
    this.currentLevel = 0;
    this.towerBuyMap = towerBuyMap;
    this.towerSellMap = towerSellMap;
    this.roadItemBuyMap = roadItemBuyMap;
    List<Integer> integerBonus = new ArrayList<>();
    for(String bonus : roundBonus){
      integerBonus.add(Integer.valueOf(bonus));
    }
    this.roundBonus = integerBonus;
  }

  // provide number of rounds and uses default starting round bonus of 100
  public Bank(Map<TowerType, Integer> towerBuyMap, Map<TowerType, Integer> towerSellMap,
      Map<RoadItemType,Integer> roadItemBuyMap, int numberOfRounds, GameMode gameMode) {
    this(towerBuyMap, towerSellMap, roadItemBuyMap, numberOfRounds, STARTING_ROUND_BONUS, gameMode);
  }


  // provide number of rounds and allows user to put in starting bonus
  public Bank(Map<TowerType, Integer> towerBuyMap, Map<TowerType, Integer> towerSellMap,
      Map<RoadItemType,Integer> roadItemBuyMap, int numberOfRounds, int starting_bonus, GameMode gameMode) {
    this.gameMode = gameMode;
    this.currentMoney = determineStartingMoney();
    this.currentLevel = 0;
    this.towerBuyMap = towerBuyMap;
    this.towerSellMap = towerSellMap;
    this.roadItemBuyMap = roadItemBuyMap;
    roundBonus = new ArrayList<>();
    for (int i = 0; i < numberOfRounds; i++) {
      roundBonus.add(i + starting_bonus);
    }
  }

  public int determineStartingMoney(){
    if(gameMode == GameMode.Sandbox){
      return STARTING_SANDBOX_MONEY;
    }
    return STARTING_MONEY;
  }

  public void advanceToLevel(int level) {
    while (currentLevel < level) {
      currentMoney += roundBonus.get(currentLevel);
      currentLevel++;
    }
  }

  public void advanceOneLevel() {
    currentMoney += roundBonus.get(currentLevel);
    System.out.println(roundBonus.get(currentLevel));
    currentLevel++;
  }

  public int getCurrentMoney() {
    return currentMoney;
  }

  public boolean buyTower(TowerType buyTower) {
    if (canBuyTower(buyTower)) {
      if(gameMode != GameMode.Sandbox){
        currentMoney -= towerBuyMap.get(buyTower);
      }
      return true;
    }
    return false;
  }



  private boolean canBuyTower(TowerType buyTower){

    return currentMoney >= towerBuyMap.get(buyTower);
  }

  public void sellTower(Tower sellTower) {
    if(gameMode != GameMode.Sandbox){
      currentMoney += towerSellMap.get(sellTower.getTowerType()) + sellTower.getTotalUpgradeCost();
    }
  }

  public void addPoppedBloonValue(){
    currentMoney += 1;
  }

  public void setSandboxValue() {
    currentMoney = STARTING_SANDBOX_MONEY;
  }

  public boolean buyUpgrade(UpgradeChoice choice, Tower buyTower){
    int cost = buyTower.getCostOfUpgrade(choice);
    if (currentMoney >= cost){
      currentMoney -= cost;
      return true;
    }
    return false;
  }

  public boolean buyRoadItem(RoadItemType buyRoadItem){
    if (canBuyRoadItem(buyRoadItem)) {
      currentMoney -= roadItemBuyMap.get(buyRoadItem);
      return true;
    }
    return false;
  }

  private boolean canBuyRoadItem(RoadItemType buyRoadItem){
    return currentMoney >= roadItemBuyMap.get(buyRoadItem);
  }

}
