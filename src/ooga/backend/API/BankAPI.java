package ooga.backend.API;

import ooga.backend.towers.Tower;

public interface BankAPI {
  void advanceToLevel(int level);
  void advanceOneLevel();
  int getCurrentMoney();
  void buyTower(Tower buyTower);
  void sellTower(Tower sellTower);
  void addPoppedBloonValue();
}
