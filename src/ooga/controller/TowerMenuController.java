package ooga.controller;

import javafx.scene.paint.Color;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import ooga.visualization.weapons.TowerNode;
import ooga.visualization.weapons.TowerNodeFactory;
import ooga.visualization.weapons.WeaponNodeFactory;
import ooga.visualization.weapons.WeaponRange;

public class TowerMenuController implements TowerMenuInterface {

  public TowerMenuController(){
  }

  @Override
  public void buyTower() {
      WeaponNodeFactory nodeFactory = new TowerNodeFactory();
      TowerNode towerInGame = nodeFactory.createTowerNode(TowerType.SingleProjectileShooter, GAME_WIDTH/2,
          GAME_HEIGHT/2, myBlockSize/2);
      WeaponRange towerRange = towerInGame.getRangeDisplay();
      myLevelLayout.getChildren().add(towerInGame);
      myLevelLayout.getChildren().add(towerRange);
      towerInGame.toFront();

      TowerFactory towerFactory = new SingleTowerFactory();

      myLevelLayout.setOnMouseMoved(e -> {
        if(e.getX() >= 0 && e.getX() <= GAME_WIDTH){
          if(e.getY() >= 0 && e.getY() <= GAME_HEIGHT){
            towerInGame.setCenterX(e.getX());
            towerInGame.setCenterY(e.getY());
            towerRange.setCenterX(e.getX());
            towerRange.setCenterY(e.getY());
          }
        }
      });
      towerInGame.setOnMouseClicked(e -> {
        myLevelLayout.setOnMouseMoved(null);
        towerRange.makeInvisible();
        myAnimationHandler.addTower(towerFactory
            .createTower(TowerType.SingleProjectileShooter, towerInGame.getCenterX(),
                towerInGame.getCenterY()), towerInGame);
        towerInGame.setOnMouseClicked(null);
      });
    System.out.println("BOUGHT!");
  }


  @Override
  public void selectTower() {

  }

  @Override
  public void sellTower() {

  }

  @Override
  public void upgradeTower() {

  }
}
