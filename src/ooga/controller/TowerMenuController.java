package ooga.controller;

import ooga.backend.bank.Bank;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.TowersCollection;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;

public class TowerMenuController implements TowerMenuInterface {

  private TowersCollection towers;
  private Bank bank;

  public TowerMenuController(TowersCollection towers, Bank bank){
    this.towers = towers;
    this.bank = bank;
  }

  @Override
  public void buyTower(TowerType towerType) {
    Boolean bought = bank.buyTower(towerType);
    if(bought){
      TowerFactory towerFactory = new SingleTowerFactory();
      Tower tower = towerFactory.createTower(towerType, 0, 0);
      towers.add(tower);
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

//  private void makeTower(TowerType towerType) {
//    WeaponNodeFactory nodeFactory = new TowerNodeFactory();
//    TowerNode towerInGame = nodeFactory.createTowerNode(towerType, gameWidth/2,
//        gameHeight/2, blockSize/2);
//    towerInGame.makeTowerMenu(this);
//    WeaponRange towerRange = towerInGame.getRangeDisplay();
//    layoutRoot.getChildren().add(towerRange);
//    layoutRoot.getChildren().add(towerInGame);
//    placeTower(towerType, towerInGame, towerRange);
//  }
//
//  private void placeTower(TowerType type, TowerNode towerNode, WeaponRange range){
//    layoutRoot.setOnMouseMoved(e -> {
//      if(e.getX() >= 0 && e.getX() <= gameWidth){
//        if(e.getY() >= 0 && e.getY() <= gameHeight){
//          towerNode.setXPosition(e.getX());
//          towerNode.setYPosition(e.getY());
//          range.setCenterX(e.getX());
//          range.setCenterY(e.getY());
//        }
//      }
//    });
//    TowerFactory towerFactory = new SingleTowerFactory();
//    towerNode.setOnMouseClicked(e -> {
//      layoutRoot.setOnMouseMoved(null);
//      range.makeInvisible();
//      animationHandler.addTower(towerFactory
//          .createTower(type, layout.getHeight() * (towerNode.getCenterX() / gameWidth),
//              layout.getWidth() * (towerNode.getCenterY() / gameHeight)), towerNode);
//      towerNode.setOnMouseClicked(null);
//      towerNode.setOnMouseClicked(h -> selectTower(towerNode));
//    });
//  }
//
//  private void openMenu(TowerNode towerNode){
//    WeaponMenu towerMenu = towerNode.getTowerMenu();
//    if(!menuPane.getChildren().contains(towerMenu)){
//      menuPane.getChildren().add(towerMenu);
//    }
//  }
}
