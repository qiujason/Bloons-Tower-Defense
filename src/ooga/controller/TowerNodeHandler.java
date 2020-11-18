package ooga.controller;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.TowersCollection;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import ooga.visualization.AnimationHandler;
import ooga.visualization.menu.WeaponMenu;
import ooga.visualization.nodes.TowerNode;
import ooga.visualization.nodes.TowerNodeFactory;
import ooga.visualization.nodes.WeaponNodeFactory;
import ooga.visualization.nodes.WeaponRange;

public class TowerNodeHandler {

  private double gameWidth;
  private double gameHeight;
  private double blockSize;
  private Group layoutRoot;
  private VBox menuPane;
  private TowerMenuInterface menuController;
  private AnimationHandler animationHandler;
  private TowersCollection towersCollection;

  private boolean canMakeTower;

  private TowerFactory towerFactory;
  private WeaponNodeFactory nodeFactory;

  private static final double towerDefaultPosition = -1;

  public TowerNodeHandler(double gameWidth, double gameHeight, double blockSize, Group layoutRoot,
      VBox menuPane, TowersCollection towersCollection, TowerMenuInterface menuController,
      AnimationHandler animationHandler) {
    this.gameWidth = gameWidth;
    this.gameHeight = gameHeight;
    this.blockSize = blockSize;
    this.layoutRoot = layoutRoot;
    this.menuPane = menuPane;
    this.towersCollection = towersCollection;
    this.menuController = menuController;
    this.animationHandler = animationHandler;

    canMakeTower = true;

    towerFactory = new SingleTowerFactory();
    nodeFactory = new TowerNodeFactory();
  }

  public void makeWeapon(TowerType towerType) {
    if (canMakeTower) {
      canMakeTower = false;
      if (menuController.buyTower(towerType)) {
        Tower tower = towerFactory
            .createTower(towerType, towerDefaultPosition, towerDefaultPosition);
        TowerNode towerNode = nodeFactory.createTowerNode(towerType, gameWidth / 2,
            gameHeight / 2, blockSize / 2);
        towerNode.makeTowerMenu(this);
        towerNode.setWeaponRange(blockSize);
        WeaponRange towerRange = towerNode.getRangeDisplay();
        layoutRoot.getChildren().add(towerRange);
        layoutRoot.getChildren().add(towerNode);
        placeTower(tower, towerNode);
      }
    }
  }

  public void removeWeapon(TowerNode towerNode) {
    Tower tower = animationHandler.getTowerFromNode(towerNode);
    menuController.sellTower(tower);
    layoutRoot.getChildren().remove(towerNode);
    layoutRoot.getChildren().remove(towerNode.getRangeDisplay());
    menuPane.getChildren().remove(towerNode.getTowerMenu());
    animationHandler.removeTower(tower, towerNode);
  }

  public void upgradeRate(TowerNode towerNode) {

  }

  public void upgradeRange(TowerNode towerNode) {

  }

  private void placeTower(Tower tower, TowerNode towerNode) {
    layoutRoot.setOnMouseMoved(e -> {
      if (e.getX() >= 0 && e.getX() <= gameWidth) {
        if (e.getY() >= 0 && e.getY() <= gameHeight) {
          towerNode.setXPosition(e.getX());
          towerNode.setYPosition(e.getY());
          towerNode.getRangeDisplay().setCenterX(e.getX());
          towerNode.getRangeDisplay().setCenterY(e.getY());
          tower.setXPosition(e.getX());
          tower.setYPosition(e.getY());
        }
      }
    });
    towerNode.setOnMouseClicked(e -> {
      layoutRoot.setOnMouseMoved(null);
      animationHandler.addTower(tower, towerNode);
      towerNode.setOnMouseClicked(null);
      selectWeapon();
      canMakeTower = true;
    });
  }

  private void selectWeapon() {
    layoutRoot.setOnMouseClicked(e -> {
      GamePieceIterator<Tower> iterator = towersCollection.createIterator();
      while (iterator.hasNext()) {
        Tower checkTower = iterator.next();
        TowerNode towerNode = animationHandler.getNodeFromTower(checkTower);
        if (e.getTarget() == towerNode) {
          openMenu(towerNode);
        } else {
          deselectWeapon(towerNode);
        }
      }
    });
  }

  private void deselectWeapon(TowerNode towerNode) {
    towerNode.hideRangeDisplay();
    menuPane.getChildren().remove(towerNode.getTowerMenu());
  }

  private void openMenu(TowerNode towerNode) {
    towerNode.showRangeDisplay();
    WeaponMenu towerMenu = towerNode.getTowerMenu();
    if (!menuPane.getChildren().contains(towerMenu)) {
      menuPane.getChildren().add(towerMenu);
    }
  }

//    @Override
//    public void upgradeRange(Tower tower){
//    }
//
//    @Override
//    public void upgradeRate(Tower tower) {
//    }
//
//    @Override
//    public void setTargetingOption(){
//    }
}
