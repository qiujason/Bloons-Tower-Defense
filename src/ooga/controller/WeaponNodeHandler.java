package ooga.controller;

import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import ooga.AlertHandler;
import ooga.backend.ConfigurationException;
import ooga.backend.GamePiece;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.layout.Layout;
import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.roaditems.factory.RoadItemFactory;
import ooga.backend.roaditems.factory.SingleRoadItemFactory;
import ooga.backend.towers.ShootingChoice;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.TowersCollection;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import ooga.backend.towers.singleshottowers.SingleShotTower;
import ooga.visualization.animationhandlers.AnimationHandler;
import ooga.visualization.menu.WeaponMenu;
import ooga.visualization.nodes.GamePieceNode;
import ooga.visualization.nodes.ItemNodeFactory;
import ooga.visualization.nodes.RoadItemNode;
import ooga.visualization.nodes.RoadItemNodeFactory;
import ooga.visualization.nodes.TowerNode;
import ooga.visualization.nodes.TowerNodeFactory;
import ooga.visualization.nodes.WeaponNodeFactory;
import ooga.visualization.nodes.WeaponRange;

/**
 * This class implements the WeaponNodeInterface and contains functionality for most of the in game
 * user inputs.
 */
public class WeaponNodeHandler implements WeaponNodeInterface {

  private final Layout layout;
  private final double gameWidth;
  private final double gameHeight;
  private final double blockSize;
  private final Group layoutRoot;
  private final VBox menuPane;
  private final WeaponBankInterface menuController;
  private final AnimationHandler animationHandler;
  private final TowersCollection towersCollection;
  private final String currentLanguage;

  private static final ResourceBundle ERROR_RESOURCES = ResourceBundle.getBundle("ErrorResource");
  private static final String PATH_ID = "Path";
  private static final String TOWER_ERROR = "TowerError";
  private static final String ITEM_ERROR = "RoadItemError";
  private static final double towerDefaultPosition = -1;
  private static final double defaultPositionDivisor = 2;
  private static final double nodeSizeDivisor = 2.5;

  private boolean canMakeTower;
  private boolean canMakeRoadItem;
  private TowerFactory towerFactory;
  private WeaponNodeFactory towerNodeFactory;
  private RoadItemFactory roadItemFactory;
  private ItemNodeFactory itemNodeFactory;

  /**
   * Constructor that initializes the necessary objects to perform the functionality for necessary
   * user inputs.
   * @param layout the game Layout
   * @param blockSize the front end size of each LayoutBlock in the Layout
   * @param layoutRoot the Group containing all the nodes in the game map
   * @param menuPane the VBox with all of the in game buttons
   * @param towersCollection collection of all the Towers in the game
   * @param menuController the WeaponBankInterface that connects all of user inputs to the Bank
   * @param animationHandler the AnimationHandler that actually animates all of the in game Nodes
   * @param language the language selected for the current game.
   */
  public WeaponNodeHandler(Layout layout, double blockSize,
      Group layoutRoot, VBox menuPane, TowersCollection towersCollection,
      WeaponBankInterface menuController, AnimationHandler animationHandler, String language) {
    this.layout = layout;
    gameHeight = layout.getHeight() * blockSize;
    gameWidth = layout.getWidth() * blockSize;
    this.blockSize = blockSize;
    this.layoutRoot = layoutRoot;
    this.menuPane = menuPane;
    this.towersCollection = towersCollection;
    this.menuController = menuController;
    this.animationHandler = animationHandler;
    canMakeTower = true;
    canMakeRoadItem = true;
    currentLanguage = language;
    initializeFactories();
  }

  /**
   * Functionality for the user input where the player purchases and places the Tower into the game.
   * Checks for if the Tower can be made and purchased and then creates the Tower in the backend
   * and then initializes the corresponding frontend TowerNode with its weapon range and tower menu.
   * Performs the drag and drop functionality where the tower follows the user's cursor before being
   * clicked and placed.
   *
   * @param towerType the TowerType that has been selected to be made in game
   */
  @Override
  public void makeWeapon(TowerType towerType) {
    if (canMakeTower) {
      if (menuController.buyTower(towerType)) {
        canMakeTower = false;
        try {
          Tower tower = towerFactory
              .createTower(towerType, towerDefaultPosition, towerDefaultPosition);
          TowerNode towerNode = towerNodeFactory.createTowerNode(towerType,
              gameWidth / defaultPositionDivisor, gameHeight / defaultPositionDivisor,
              blockSize / nodeSizeDivisor);
          towerNode.makeTowerMenu(this, currentLanguage);
          towerNode.setWeaponRange(tower.getRadius(), blockSize);
          WeaponRange towerRange = towerNode.getRangeDisplay();
          layoutRoot.getChildren().add(towerRange);
          layoutRoot.getChildren().add(towerNode);
          placeTower(tower, towerNode);
        } catch (ConfigurationException e) {
          new AlertHandler(ERROR_RESOURCES.getString(TOWER_ERROR),
              ERROR_RESOURCES.getString(e.getMessage()));
        }
      }
    }
  }

  /**
   * Functionality for the user input where the player purchases and places the road item into the game.
   * Checks for if the road item can be made and purchased and then creates both the road item in the backend
   * and the corresponding frontend road item node. Performs the drag and drop functionality where the
   * road item follows the user's cursor before being clicked and placed.
   *
   * @param roadItemType the RoadItemType that has been selected to be made in game
   */
  @Override
  public void makeRoadWeapon(RoadItemType roadItemType) {
    if (canMakeRoadItem) {
      if (menuController.buyRoadItem(roadItemType)) {
        canMakeRoadItem = false;
        try {
          RoadItem roadItem = roadItemFactory
              .createRoadItem(roadItemType, towerDefaultPosition, towerDefaultPosition);
          RoadItemNode itemNode = itemNodeFactory.createItemNode(roadItemType,
              gameWidth / defaultPositionDivisor,
              gameHeight / defaultPositionDivisor, blockSize / nodeSizeDivisor);
          layoutRoot.getChildren().add(itemNode);
          placeRoadItem(roadItem, itemNode);
        } catch (ConfigurationException e) {
          new AlertHandler(ERROR_RESOURCES.getString(ITEM_ERROR),
              ERROR_RESOURCES.getString(e.getMessage()));
        }
      }
    }
  }

  /**
   * This method contains the functionality to remove the tower from the game. When the user
   * clicks on the SellTower button, it removes the Tower from both the backend and front end as well
   * as performing the proper functionality with the Bank.
   *
   * @param towerNode the TowerNode of the Tower being sold.
   */
  @Override
  public void removeWeapon(TowerNode towerNode) {
    Tower tower = animationHandler.getTowerFromNode(towerNode);
    menuController.sellTower(tower);
    layoutRoot.getChildren().remove(towerNode);
    layoutRoot.getChildren().remove(towerNode.getRangeDisplay());
    menuPane.getChildren().remove(towerNode.getTowerMenu());
    animationHandler.removeTower(tower, towerNode);
  }

  /**
   * Method that takes in the TowerNode that has been selected for a shooting rate upgrade and
   * performs it.
   *
   * @param towerNode the TowerNode that has been selected for a shooting rate upgrade
   */
  @Override
  public void upgradeRate(TowerNode towerNode) {
    Tower tower = animationHandler.getTowerFromNode(towerNode);
    menuController.upgradeRate(tower);
  }

  /**
   * Method that takes in the TowerNode that has been selected for a range upgrade and
   * performs it.
   *
   * @param towerNode the TowerNode that has been selected for a range upgrade
   */
  @Override
  public void upgradeRange(TowerNode towerNode) {
    Tower tower = animationHandler.getTowerFromNode(towerNode);
    menuController.upgradeRange(tower);
    towerNode.setWeaponRange(tower.getRadius(), blockSize);
  }

  /**
   * Takes in the TowerNode and the shooting choice that the Player selects and changes the Tower's
   * shooting priority.
   *
   * @param towerNode the selected TowerNode
   * @param shootingChoice the ShootingChoice that the Player select
   */
  @Override
  public void setTargetingOption(TowerNode towerNode, ShootingChoice shootingChoice) {
    Tower tower = animationHandler.getTowerFromNode(towerNode);
    SingleShotTower singleShotTower = (SingleShotTower) tower;
    singleShotTower.updateShootingChoice(shootingChoice);
  }

  private void placeTower(GamePiece gamePiece, GamePieceNode pieceNode) {
    Tower tower = (Tower) gamePiece;
    TowerNode towerNode = (TowerNode) pieceNode;
    layoutRoot.setOnMouseMoved(e -> {
      if (e.getX() >= 0 && e.getX() <= gameWidth) {
        if (e.getY() >= 0 && e.getY() <= gameHeight) {
          setNode(tower, towerNode, e.getX(), e.getY());
        }
        if (checkInvalidPlacement(towerNode)) {
          towerNode.getRangeDisplay().invalidPlacement();
        } else {
          towerNode.getRangeDisplay().validPlacement();
        }
      }
    });
    towerNode.setOnMouseClicked(e -> setDownTower(tower, towerNode));
  }

  private void placeRoadItem(GamePiece roadItem, GamePieceNode roadItemNode) {
    layoutRoot.setOnMouseMoved(e -> {
      if (e.getX() >= 0 && e.getX() <= gameWidth) {
        if (e.getY() >= 0 && e.getY() <= gameHeight) {
          setNode(roadItem, roadItemNode, e.getX(), e.getY());
        }
      }
    });
    roadItemNode.setOnMouseClicked(e -> setDownRoadItem(roadItem, roadItemNode));
  }

  private void setNode(GamePiece gamePiece, GamePieceNode pieceNode, double xPos, double yPos) {
    pieceNode.setXPosition(xPos);
    pieceNode.setYPosition(yPos);
    gamePiece.setXPosition(toGridXPosition(xPos));
    gamePiece.setYPosition(toGridYPosition(yPos));
  }

  private void setDownTower(Tower tower, TowerNode towerNode) {
    if (!checkInvalidPlacement(towerNode)) {
      layoutRoot.setOnMouseMoved(null);
      animationHandler.addTower(tower, towerNode);
      towerNode.setOnMouseClicked(null);
      selectWeapon();
      canMakeTower = true;
    }
  }

  private void setDownRoadItem(GamePiece roadItem, GamePieceNode roadItemNode) {
    if (checkOnPath(roadItemNode)) {
      layoutRoot.setOnMouseMoved(null);
      animationHandler.addRoadItem((RoadItem) roadItem, (RoadItemNode) roadItemNode);
      roadItemNode.setOnMouseClicked(null);
      canMakeRoadItem = true;
    }
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

  private double toGridXPosition(double gameXPosition) {
    return layout.getWidth() * gameXPosition / gameWidth;
  }

  private double toGridYPosition(double gameYPosition) {
    return layout.getHeight() * gameYPosition / gameHeight;
  }

  private boolean checkInvalidPlacement(TowerNode towerNode) {
    return checkOnPath(towerNode) || checkOverlapTower(towerNode);
  }

  private boolean checkOnPath(GamePieceNode gameNode) {
    for (Node layoutBlock : layoutRoot.getChildren()) {
      if (layoutBlock.getId() != null && layoutBlock.getId().contains(PATH_ID)) {
        if (gameNode.getBoundsInParent().intersects(layoutBlock.getBoundsInParent())) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkOverlapTower(TowerNode towerNode) {
    GamePieceIterator<Tower> towerIterator = towersCollection.createIterator();
    while (towerIterator.hasNext()) {
      TowerNode checkTowerNode = animationHandler.getNodeFromTower(towerIterator.next());
      if (towerNode.getBoundsInParent().intersects(checkTowerNode.getBoundsInParent())) {
        return true;
      }
    }
    return false;
  }

  private void initializeFactories() {
    towerFactory = new SingleTowerFactory();
    towerNodeFactory = new TowerNodeFactory();
    roadItemFactory = new SingleRoadItemFactory();
    itemNodeFactory = new RoadItemNodeFactory();
  }
}
