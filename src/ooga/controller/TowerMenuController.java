package ooga.controller;

import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import ooga.visualization.AnimationHandler;
import ooga.visualization.menu.WeaponMenu;
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
  private VBox menuPane;
  private WeaponMenu towerMenu;

  public TowerMenuController(double gameWidth, double gameHeight, double blockSize, Group layoutRoot,
      AnimationHandler animationHandler, VBox menuPane){
    this.gameWidth = gameWidth;
    this.gameHeight = gameHeight;
    this.blockSize = blockSize;
    this.layoutRoot = layoutRoot;
    this.animationHandler = animationHandler;
    this.menuPane = menuPane;
  }

  @Override
  public void buyTower(TowerType towerType) {
    makeTower(towerType);
  }

  @Override
  public void selectTower(TowerNode tower) {
    tower.showRangeDisplay();
    openMenu(tower);
  }

  @Override
  public void sellTower(TowerNode tower) {
    layoutRoot.getChildren().remove(tower.getRangeDisplay());
    layoutRoot.getChildren().remove(tower);
  }

  @Override
  public void upgradeTower(TowerNode tower) {

  }

  @Override
  public void closeMenu(){
    layoutRoot.getChildren().remove(towerMenu);
  }

  private void makeTower(TowerType towerType) {
    WeaponNodeFactory nodeFactory = new TowerNodeFactory();
    TowerNode towerInGame = nodeFactory.createTowerNode(towerType, gameWidth/2,
        gameHeight/2, blockSize/2);
    towerInGame.makeTowerMenu(this);
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
    TowerFactory towerFactory = new SingleTowerFactory();
    tower.setOnMouseClicked(e -> {
      layoutRoot.setOnMouseMoved(null);
      range.makeInvisible();
      animationHandler.addTower(towerFactory
          .createTower(type, 14 * (tower.getCenterX() / gameWidth),
              9 * (tower.getCenterY() / gameHeight)), tower);
      tower.setOnMouseClicked(null);
      tower.setOnMouseClicked(h -> selectTower(tower));
    });
  }

  private void openMenu(TowerNode tower){
    towerMenu = tower.getTowerMenu();
    if(!menuPane.getChildren().contains(towerMenu)){
      menuPane.getChildren().add(towerMenu);
    }
  }
}
