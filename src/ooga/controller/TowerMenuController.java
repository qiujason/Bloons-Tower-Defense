package ooga.controller;

import java.util.ResourceBundle;
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
    WeaponNodeFactory nodeFactory = new TowerNodeFactory();
    TowerNode towerInGame = nodeFactory.createTowerNode(towerType, gameWidth/2,
          gameHeight/2, blockSize/2);
    WeaponRange towerRange = towerInGame.getRangeDisplay();
    layoutRoot.getChildren().add(towerRange);
    layoutRoot.getChildren().add(towerInGame);

    TowerFactory towerFactory = new SingleTowerFactory();
    layoutRoot.setOnMouseMoved(e -> {
      if(e.getX() >= 0 && e.getX() <= gameWidth){
        if(e.getY() >= 0 && e.getY() <= gameHeight){
          towerInGame.setCenterX(e.getX());
          towerInGame.setCenterY(e.getY());
          towerRange.setCenterX(e.getX());
          towerRange.setCenterY(e.getY());
        }
      }
    });
    towerInGame.setOnMouseClicked(e -> {
      layoutRoot.setOnMouseMoved(null);
      towerRange.makeInvisible();
      animationHandler.addTower(towerFactory
          .createTower(towerType, towerInGame.getCenterX(),
              towerInGame.getCenterY()), towerInGame);
      towerInGame.setOnMouseClicked(null);
    });
  }


  @Override
  public void selectTower(TowerNode tower) {

  }

  @Override
  public void sellTower() {

  }

  @Override
  public void upgradeTower() {

  }
}
