package ooga.controller;

import javafx.scene.Group;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import ooga.visualization.AnimationHandler;
import ooga.visualization.nodes.GamePieceNode;
import ooga.visualization.nodes.TowerNode;
import ooga.visualization.nodes.TowerNodeFactory;
import ooga.visualization.nodes.WeaponNodeFactory;
import ooga.visualization.nodes.WeaponRange;

public class TowerNodeHandler {

  private double gameWidth;
  private double gameHeight;
  private double blockSize;
  private Group layoutRoot;
  private AnimationHandler animationHandler;

  private TowerFactory towerFactory;
  private WeaponNodeFactory nodeFactory;

  private static final double towerDefaultPosition = -1;

  public TowerNodeHandler(double gameWidth, double gameHeight, double blockSize, Group layoutRoot,
      AnimationHandler animationHandler){
    this.gameWidth = gameWidth;
    this.gameHeight = gameHeight;
    this.blockSize = blockSize;
    this.layoutRoot = layoutRoot;
    this.animationHandler = animationHandler;

    towerFactory = new SingleTowerFactory();
    nodeFactory = new TowerNodeFactory();
  }

  public GamePieceNode makeWeaponNode(TowerType towerType){
    Tower tower = towerFactory
        .createTower(towerType, towerDefaultPosition, towerDefaultPosition);
    TowerNode towerNode = nodeFactory.createTowerNode(towerType, gameWidth/2,
        gameHeight/2, blockSize/2);
    towerNode.setWeaponRange(blockSize);
    WeaponRange towerRange = towerNode.getRangeDisplay();
    layoutRoot.getChildren().add(towerRange);
    layoutRoot.getChildren().add(towerNode);
    placeTower(towerNode, towerRange);
    return towerNode;
  }

  @Override
  public void selectWeapon(TowerNode tower) {
    tower.showRangeDisplay();
//    openMenu(tower);
  }

//  public void closeMenu(TowerNode towerNode){
//    menuPane.getChildren().remove(towerNode.getTowerMenu());
//    towerNode.hideRangeDisplay();
//  }


  private void placeTower(TowerNode towerNode, WeaponRange range){
    layoutRoot.setOnMouseMoved(e -> {
      if(e.getX() >= 0 && e.getX() <= gameWidth){
        if(e.getY() >= 0 && e.getY() <= gameHeight){
          towerNode.setXPosition(e.getX());
          towerNode.setYPosition(e.getY());
          range.setCenterX(e.getX());
          range.setCenterY(e.getY());
        }
      }
    });
    towerNode.setOnMouseClicked(e -> {
      layoutRoot.setOnMouseMoved(null);
      range.makeInvisible();
      towerNode.setOnMouseClicked(null);
      towerNode.setOnMouseClicked(h -> selectWeapon(towerNode));
    });
  }

//  private void openMenu(TowerNode towerNode){
//    WeaponMenu towerMenu = towerNode.getTowerMenu();
//    if(!menuPane.getChildren().contains(towerMenu)){
//      menuPane.getChildren().add(towerMenu);
//    }
//  }


}
