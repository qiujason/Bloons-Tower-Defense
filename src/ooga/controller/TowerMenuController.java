package ooga.controller;

import javafx.scene.Group;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import ooga.visualization.AnimationHandler;
import ooga.visualization.nodes.TowerNode;
import ooga.visualization.nodes.TowerNodeFactory;
import ooga.visualization.nodes.WeaponNodeFactory;
import ooga.visualization.nodes.WeaponRange;

public class TowerMenuController implements TowerMenuInterface {

  private double gameWidth;
  private double gameHeight;
  private double blockSize;
  private Group layoutRoot;
  private AnimationHandler animationHandler;

  public TowerMenuController(double gameWidth, double gameHeight, double blockSize, Group layoutRoot,
      AnimationHandler animationHandler){
    this.gameWidth = gameWidth;
    this.gameHeight = gameHeight;
    this.blockSize = blockSize;
    this.layoutRoot = layoutRoot;
    this.animationHandler = animationHandler;
  }

  @Override
  public void buyTower(TowerType towerType) {
    makeTower(towerType);
  }

  @Override
  public void selectTower(TowerNode tower) {
    if(tower.rangeShown()){
      tower.hideRangeDisplay();
    }
    else{
      tower.showRangeDisplay();
    }
  }

  @Override
  public void sellTower() {

  }

  @Override
  public void upgradeTower() {

  }

  private void makeTower(TowerType towerType) {
    WeaponNodeFactory nodeFactory = new TowerNodeFactory();
    TowerNode towerInGame = nodeFactory.createTowerNode(towerType, gameWidth/2,
        gameHeight/2, blockSize/2);
    WeaponRange towerRange = towerInGame.getRangeDisplay();
    layoutRoot.getChildren().add(towerRange);
    layoutRoot.getChildren().add(towerInGame);
    placeTower(towerType, towerInGame, towerRange);
  }

  private void placeTower(TowerType type, TowerNode tower, WeaponRange range){
    layoutRoot.setOnMouseMoved(e -> {
      if(e.getX() >= 0 && e.getX() <= gameWidth){
        if(e.getY() >= 0 && e.getY() <= gameHeight){
          tower.setXPosition(e.getX());
          tower.setYPosition(e.getY());
          range.setCenterX(e.getX());
          range.setCenterY(e.getY());
        }
      }
    });
    tower.setOnMouseClicked(e -> {
      layoutRoot.setOnMouseMoved(null);
      range.makeInvisible();
      TowerFactory towerFactory = new SingleTowerFactory();
      animationHandler.addTower(towerFactory
          .createTower(type, 14 * (tower.getCenterX() / gameWidth),
              9 * (tower.getCenterY() / gameHeight)), tower);
      tower.setOnMouseClicked(null);
      tower.setOnMouseClicked(h -> selectTower(tower));
    });

  }
}
