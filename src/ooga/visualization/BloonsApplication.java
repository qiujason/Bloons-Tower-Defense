package ooga.visualization;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.backend.layout.Layout;
import ooga.backend.layout.LayoutBlock;
import ooga.backend.readers.LayoutReader;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import ooga.backend.towers.singleshottowers.SingleProjectileShooter;
import ooga.controller.GameMenuController;
import ooga.controller.GameMenuInterface;
import ooga.controller.TowerMenuController;
import ooga.controller.TowerMenuInterface;

public class BloonsApplication extends Application {

  public static final double HEIGHT = 500;
  public static final double WIDTH = 800;
  public static final double GAME_HEIGHT = 0.875 * HEIGHT;
  public static final double GAME_WIDTH = 0.75 * WIDTH;
  public static final String LAYOUTS_PATH = "layouts/";
  public static final String LEVEL_FILE = LAYOUTS_PATH + "level1.csv";
  public static final String TOWER_IMAGE = "/gamePhotos/dartmonkey.png";

  private Stage myStage;
  private Scene myScene;
  private Layout myLayout;
  private Map<Node, Node> blockToTower;
  private LayoutReader myLayoutReader;
  private Group myLevelLayout;
  private GameMenu myMenu;
  private GameMenuInterface gameMenuController;
  private TowerMenuInterface towerMenuController;
  private AnimationHandler myAnimationHandler;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;
  private final ResourceBundle myBlockMappings = ResourceBundle
      .getBundle(getClass().getPackageName() + ".resources.blockMappings");

  @Override
  public void start(Stage mainStage) {
    myStage = mainStage;
    BorderPane menuLayout = new BorderPane();
    setupMenuLayout(menuLayout);
    myScene = new Scene(menuLayout, WIDTH, HEIGHT);
    myStage.setScene(myScene);
    myStage.show();
  }

  private void setupMenuLayout(BorderPane menu) {
    Text titleText = new Text("Bloons Tower Defense");
    titleText.setScaleX(3);
    titleText.setScaleY(3);
    menu.setCenter(titleText);
    Button startButton = new Button();
    startButton.setOnAction(e -> loadLevel());
    startButton.setText("Start");
    startButton.setId("Start");
    BorderPane.setAlignment(startButton, Pos.CENTER);
    menu.setBottom(startButton);
    menu.setBackground(new Background(new BackgroundFill(Color.web("#83b576"), null, null)));
  }

  private void loadLevel() {
    BorderPane level = new BorderPane();
    myLayoutReader = new LayoutReader();
    visualizeLayout(level);
    myAnimationHandler = new AnimationHandler(myLayout, myLevelLayout, myStartingX, myStartingY, myBlockSize);
    gameMenuController = new GameMenuController(myAnimationHandler.getAnimation());
    towerMenuController = new TowerMenuController();
    blockToTower = new HashMap<>();
    visualizePlayerGUI(level);
    level.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
    myScene = new Scene(level, WIDTH, HEIGHT);
    myStage.setScene(myScene);
  }


  // TODO: Refactor
  private void visualizeLayout(BorderPane level) {
    myLevelLayout = new Group();
    level.setLeft(myLevelLayout);

    myLayout = myLayoutReader.generateLayout(LEVEL_FILE);

    int numberOfRows = myLayout.getHeight();
    int numberOfColumns = myLayout.getWidth();
    double blockWidth = GAME_WIDTH / numberOfColumns;
    double blockHeight = GAME_HEIGHT / numberOfRows;
    myBlockSize = Math.min(blockWidth, blockHeight);
    System.out.println(myBlockSize);

    double currentBlockX = 0;
    double currentBlockY = 0;
    int blockNumberX = 0;
    int blockNumberY = 0;

    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        Rectangle newBlock = createBlock(myLayout.getBlock(i,j).getBlockType(), currentBlockX, currentBlockY, myBlockSize);
        newBlock.setId("LayoutBlock" + blockNumberX + blockNumberY);
        myLevelLayout.getChildren().add(newBlock);
        currentBlockX += myBlockSize;
        blockNumberX++;
      }
      currentBlockX = 0;
      blockNumberX = 0;
      currentBlockY += myBlockSize;
      blockNumberY++;
    }
  }

  private Rectangle createBlock(String block, double currentBlockX, double currentBlockY,
      double blockSize) {
    Rectangle blockRectangle = new Rectangle(currentBlockX, currentBlockY, blockSize, blockSize);
    String blockColorAsString = myBlockMappings.getString(block);
    Color blockColor = Color.web(blockColorAsString);
    blockRectangle.setFill(blockColor);
//    blockRectangle.setOnMouseClicked(e -> putTower(blockRectangle));
    if(block.charAt(0) == '*') {
      myStartingX = currentBlockX + blockSize / 2;
      myStartingY = currentBlockY + blockSize / 2;
    }
    return blockRectangle;
  }

  public void createTower(){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(TOWER_IMAGE).toURI()));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    assert towerImage != null;
    ImagePattern towerImagePattern = new ImagePattern(towerImage);
    Circle towerInGame = new Circle(50,50,myBlockSize / 2);
    towerInGame.setFill(towerImagePattern);
    myLevelLayout.getChildren().add(towerInGame);
    towerInGame.setOnMouseMoved(e -> {
      towerInGame.setCenterX(e.getX());
      towerInGame.setCenterY(e.getY());
    });
    towerInGame.setOnMouseClicked(e ->{
      towerInGame.setOnMouseMoved(null);
      towerInGame.setCenterX(e.getX());
      towerInGame.setCenterY(e.getY());
      TowerFactory towerFactory = new SingleTowerFactory();
      myAnimationHandler.addTower(towerFactory
          .createTower(TowerType.SingleProjectileShooter, e.getX(),
              e.getY()), towerInGame);
    });
  }

  // TODO: handle exception/refactor
//  private void putTower(Rectangle blockRectangle) {
//    Color playableBlock = Color.valueOf(myBlockMappings.getString("0"));
//    Color nonPlayableBlock = Color.valueOf(myBlockMappings.getString(">"));
//    if (blockRectangle.getFill().equals(playableBlock) && !blockToTower.containsKey(blockRectangle)) {
//      Image towerImage = null;
//      try {
//        towerImage = new Image(String.valueOf(getClass().getResource(TOWER_IMAGE).toURI()));
//      } catch (URISyntaxException e) {
//        e.printStackTrace();
//      }
//      assert towerImage != null;
//      ImagePattern towerImagePattern = new ImagePattern(towerImage);
//      Circle towerInGame = new Circle(blockRectangle.getX() + myBlockSize / 2, blockRectangle.getY() + myBlockSize / 2, myBlockSize / 2);
//      towerInGame.setFill(towerImagePattern);
//      towerInGame.setId(blockRectangle.getId() + "Tower");
//      towerInGame.setOnMouseClicked(e -> myAnimationHandler.removeTower(towerInGame));
//      blockToTower.put(blockRectangle, towerInGame);
//      TowerFactory towerFactory = new SingleTowerFactory();
//      myAnimationHandler.addTower(towerFactory
//          .createTower(TowerType.SingleProjectileShooter, blockRectangle.getX() + myBlockSize / 2,
//              blockRectangle.getY() + myBlockSize / 2), towerInGame);
//    } else if (!blockRectangle.getFill().equals(nonPlayableBlock)) {
//      blockToTower.remove(blockRectangle);
//    }
//    else {
//      myAnimationHandler.getAnimation().pause();
//      makeAlert("Invalid Tower Space", "You cannot place a tower there :(");
//    }
//  }

  private void visualizePlayerGUI(BorderPane level) {
    VBox menuPane = new VBox();
    menuPane.setSpacing(10); //magic num
    myMenu = new GameMenu(this, menuPane, gameMenuController, towerMenuController, myAnimationHandler);
    level.setRight(menuPane);
  }

  /**
   * This class makes a new alert message when there is an error.
   * @param header
   * @param message
   */
  public void makeAlert(String header, String message) {
    Alert a = new Alert(Alert.AlertType.NONE);
    ButtonType close = new ButtonType(":(", ButtonBar.ButtonData.CANCEL_CLOSE);
    a.getButtonTypes().addAll(close);
    a.setHeaderText(header);
    a.setContentText(message);
    a.show();
  }

}
