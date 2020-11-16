package ooga.controller;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import ooga.backend.layout.Layout;
import ooga.backend.towers.Tower;
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

  private Layout layout;
  private double gameWidth;
  private double gameHeight;
  private double blockSize;
  private Group layoutRoot;
  private AnimationHandler animationHandler;
  private VBox menuPane;

  public TowerMenuController(Layout layout, double gameWidth, double gameHeight, double blockSize, Group layoutRoot,
      AnimationHandler animationHandler, VBox menuPane){
    this.layout = layout;
    this.gameWidth = gameWidth;
    this.gameHeight = gameHeight;
    this.blockSize = blockSize;
    this.layoutRoot = layoutRoot;
    this.animationHandler = animationHandler;
    this.menuPane = menuPane;
  }

  @Override
  public void buyTower(TowerType towerType) {
    //money
    makeTower(towerType);
  }

  @Override
  public void selectTower(TowerNode tower) {
    tower.showRangeDisplay();
    openMenu(tower);
  }

  @Override
  public void sellTower(TowerNode towerNode) {
    layoutRoot.getChildren().remove(towerNode);
    layoutRoot.getChildren().remove(towerNode.getRangeDisplay());
    animationHandler.removeTower(towerNode);
    closeMenu(towerNode);
  }

  @Override
  public void upgradeTower(TowerNode tower) {

  }

  @Override
  public void closeMenu(TowerNode towerNode){
    menuPane.getChildren().remove(towerNode.getTowerMenu());
    towerNode.hideRangeDisplay();
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

  private void placeTower(TowerType type, TowerNode towerNode, WeaponRange range){
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
    TowerFactory towerFactory = new SingleTowerFactory();
    towerNode.setOnMouseClicked(e -> {
      layoutRoot.setOnMouseMoved(null);
      range.makeInvisible();
      animationHandler.addTower(towerFactory
          .createTower(type, layout.getHeight() * (towerNode.getCenterX() / gameWidth),
              layout.getWidth() * (towerNode.getCenterY() / gameHeight)), towerNode);
      towerNode.setOnMouseClicked(null);
      towerNode.setOnMouseClicked(h -> selectTower(towerNode));
    });
  }

  private void openMenu(TowerNode towerNode){
    WeaponMenu towerMenu = towerNode.getTowerMenu();
    if(!menuPane.getChildren().contains(towerMenu)){
      menuPane.getChildren().add(towerMenu);
    }
  }
}
