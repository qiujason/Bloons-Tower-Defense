package ooga.controller;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.layout.Layout;
import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.roaditems.factory.RoadItemFactory;
import ooga.backend.roaditems.factory.SingleRoadItemFactory;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.TowersCollection;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import ooga.visualization.AnimationHandler;
import ooga.visualization.menu.WeaponMenu;
import ooga.visualization.nodes.ItemNodeFactory;
import ooga.visualization.nodes.RoadItemNode;
import ooga.visualization.nodes.RoadItemNodeFactory;
import ooga.visualization.nodes.TowerNode;
import ooga.visualization.nodes.TowerNodeFactory;
import ooga.visualization.nodes.WeaponNodeFactory;
import ooga.visualization.nodes.WeaponRange;

public class TowerNodeHandler {

  private Layout layout;
  private double gameWidth;
  private double gameHeight;
  private double blockSize;
  private Group layoutRoot;
  private VBox menuPane;
  private TowerMenuInterface menuController;
  private AnimationHandler animationHandler;
  private TowersCollection towersCollection;

  private boolean canMakeTower;
  private boolean canMakeRoadItem;

  private TowerFactory towerFactory;
  private WeaponNodeFactory towerNodeFactory;
  private RoadItemFactory roadItemFactory;
  private ItemNodeFactory itemNodeFactory;

  private static final double towerDefaultPosition = -1;

  public TowerNodeHandler(Layout layout, double gameWidth, double gameHeight, double blockSize,
      Group layoutRoot, VBox menuPane, TowersCollection towersCollection,
      TowerMenuInterface menuController, AnimationHandler animationHandler) {
    this.layout = layout;
    this.gameWidth = gameWidth;
    this.gameHeight = gameHeight;
    this.blockSize = blockSize;
    this.layoutRoot = layoutRoot;
    this.menuPane = menuPane;
    this.towersCollection = towersCollection;
    this.menuController = menuController;
    this.animationHandler = animationHandler;

    canMakeTower = true;
    canMakeRoadItem = true;

    towerFactory = new SingleTowerFactory();
    towerNodeFactory = new TowerNodeFactory();
    roadItemFactory = new SingleRoadItemFactory();
    itemNodeFactory = new RoadItemNodeFactory();
  }

  public void makeWeapon(TowerType towerType) {
    if (canMakeTower) {
      canMakeTower = false;
      if (menuController.buyTower(towerType)) {
        Tower tower = towerFactory
            .createTower(towerType, towerDefaultPosition, towerDefaultPosition);
        TowerNode towerNode = towerNodeFactory.createTowerNode(towerType, gameWidth / 2,
            gameHeight / 2, blockSize / 2);
        towerNode.makeTowerMenu(this);
        towerNode.setWeaponRange(tower.getRadius(), blockSize);
        WeaponRange towerRange = towerNode.getRangeDisplay();
        layoutRoot.getChildren().add(towerRange);
        layoutRoot.getChildren().add(towerNode);
        placeTower(tower, towerNode);
      }
      System.out.println("make more money cuh");
    }
  }

  public void makeRoadWeapon(RoadItemType roadItemType){
    if (canMakeRoadItem){
      canMakeRoadItem = false;
      RoadItem roadItem = roadItemFactory
          .createTower(roadItemType, towerDefaultPosition, towerDefaultPosition);
      RoadItemNode itemNode = itemNodeFactory.createItemNode(roadItemType, gameWidth / 2,
          gameHeight / 2, blockSize /2);
      layoutRoot.getChildren().add(itemNode);
      placeRoadItem(roadItem, itemNode);
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
    Tower tower = animationHandler.getTowerFromNode(towerNode);
    menuController.upgradeRate(tower);
  }

  public void upgradeRange(TowerNode towerNode) {
    Tower tower = animationHandler.getTowerFromNode(towerNode);
    menuController.upgradeRange(tower);
    towerNode.setWeaponRange(tower.getRadius(), blockSize);
  }

  private void placeTower(Tower tower, TowerNode towerNode) {
    layoutRoot.setOnMouseMoved(e -> {
      if (e.getX() >= 0 && e.getX() <= gameWidth) {
        if (e.getY() >= 0 && e.getY() <= gameHeight) {
          towerNode.setXPosition(e.getX());
          towerNode.setYPosition(e.getY());
          tower.setXPosition(toGridXPosition(e.getX()));
          tower.setYPosition(toGridYPosition(e.getY()));
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

  private void placeRoadItem(RoadItem roadItem, RoadItemNode itemNode){
    layoutRoot.setOnMouseMoved(e -> {
      if (e.getX() >= 0 && e.getX() <= gameWidth) {
        if (e.getY() >= 0 && e.getY() <= gameHeight) {
          itemNode.setXPosition(e.getX());
          itemNode.setYPosition(e.getY());
          roadItem.setXPosition(toGridXPosition(e.getX()));
          roadItem.setYPosition(toGridYPosition(e.getY()));
        }
      }
    });
    itemNode.setOnMouseClicked(e -> {
      layoutRoot.setOnMouseMoved(null);
//      animationHandler.addRoadItem(roadItem, itemNode);
      itemNode.setOnMouseClicked(null);
      canMakeRoadItem = true;
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

  private double toGridXPosition(double gameXPosition){
    return layout.getWidth() * gameXPosition / gameWidth;
  }

  private double toGridYPosition(double gameYPosition){
    return layout.getHeight() * gameYPosition / gameHeight;
  }

//    @Override
//    public void setTargetingOption(){
//    }
}
