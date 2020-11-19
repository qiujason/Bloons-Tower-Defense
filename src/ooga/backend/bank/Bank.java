package ooga.backend.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.backend.API.BankAPI;
import ooga.backend.roaditems.RoadItem;
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

  public static int STARTING_ROUND_BONUS = 100;
  public static int STARTING_MONEY = 1000;

  private int currentMoney = 1000;
  private int currentLevel = 0;
  private int numberOfTotalRounds;
  private List<Integer> roundBonus;
  private Map<TowerType, Integer> towerBuyMap;
  private Map<TowerType, Integer> towerSellMap;
  private Map<RoadItemType,Integer> roadItemBuyMap;

  // Provided list of round bonuses read from csv
  public Bank(Map<TowerType, Integer> towerBuyMap, Map<TowerType, Integer> towerSellMap,
      Map<RoadItemType,Integer> roadItemBuyMap,List<String> roundBonus) {
    this.towerBuyMap = towerBuyMap;
    this.towerSellMap = towerSellMap;
    this.roadItemBuyMap = roadItemBuyMap;
    numberOfTotalRounds = roundBonus.size();
    List<Integer> integerBonus = new ArrayList<>();
    for(String bonus : roundBonus){
      integerBonus.add(Integer.valueOf(bonus));
    }
    this.roundBonus = integerBonus;
  }

  // provide number of rounds and uses default starting round bonus of 100
  public Bank(Map<TowerType, Integer> towerBuyMap, Map<TowerType, Integer> towerSellMap,
      Map<RoadItemType,Integer> roadItemBuyMap, int numberOfRounds) {
    this(towerBuyMap, towerSellMap, roadItemBuyMap, numberOfRounds, STARTING_ROUND_BONUS);
  }

  // provide number of rounds and allows user to put in starting bonus
  public Bank(Map<TowerType, Integer> towerBuyMap, Map<TowerType, Integer> towerSellMap,
      Map<RoadItemType,Integer> roadItemBuyMap, int numberOfRounds, int starting_bonus) {
    this.towerBuyMap = towerBuyMap;
    this.towerSellMap = towerSellMap;
    this.roadItemBuyMap = roadItemBuyMap;
    numberOfTotalRounds = numberOfRounds;
    roundBonus = new ArrayList<>();
    for (int i = 0; i < numberOfRounds; i++) {
      roundBonus.add(i + starting_bonus);
    }
  }

  public void advanceToLevel(int level) {
    while (currentLevel < level) {
      currentMoney += roundBonus.get(currentLevel);
      currentLevel++;
    }
  }

  public void advanceOneLevel() {
    currentMoney += roundBonus.get(currentLevel);
    currentLevel++;
  }

  public int getCurrentMoney() {
    return currentMoney;
  }

  public boolean buyTower(TowerType buyTower) {
    if (canBuyTower(buyTower)) {
      currentMoney -= towerBuyMap.get(buyTower);
      return true;
    }
    return false;
  }

  private boolean canBuyTower(TowerType buyTower){
    return currentMoney >= towerBuyMap.get(buyTower);
  }

  public void sellTower(Tower sellTower) {
    currentMoney += towerSellMap.get(sellTower.getTowerType()) + sellTower.getTotalUpgradeCost();
  }

  public void addPoppedBloonValue(){
    currentMoney += 1;
  }

  public boolean buyUpgrade(UpgradeChoice choice, Tower buyTower){
    int cost = buyTower.getCostOfUpgrade(choice);
    if (currentMoney >= cost){
      currentMoney -= cost;
      return true;
    }
    return false;
  }

  public boolean buyRoadItem(RoadItem buyRoadItem){
    if (canBuyRoadItem(buyRoadItem)) {
      currentMoney -= roadItemBuyMap.get(buyRoadItem);
      return true;
    }
    return false;
  }

  private boolean canBuyRoadItem(RoadItem buyRoadItem){
    return currentMoney >= roadItemBuyMap.get(buyRoadItem);
  }

}
