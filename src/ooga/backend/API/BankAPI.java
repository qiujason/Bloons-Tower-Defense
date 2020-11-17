package ooga.backend.API;

import ooga.backend.towers.TowerType;

public interface BankAPI {
  void advanceToLevel(int level);
  void advanceOneLevel();
  int getCurrentMoney();
  boolean buyTower(TowerType buyTower);
  void sellTower(TowerType sellTower);
  void addPoppedBloonValue();
}
