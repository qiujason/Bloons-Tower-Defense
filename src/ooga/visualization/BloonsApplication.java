package ooga.visualization;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.layout.Layout;
import ooga.backend.readers.LayoutReader;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import ooga.controller.GameMenuController;
import ooga.controller.GameMenuInterface;
import ooga.controller.TowerMenuController;
import ooga.controller.TowerMenuInterface;
import ooga.visualization.menu.GameMenu;
import ooga.visualization.weapons.TowerNode;
import ooga.visualization.weapons.TowerNodeFactory;
import ooga.visualization.weapons.WeaponNodeFactory;
import ooga.visualization.weapons.WeaponRange;

public class BloonsApplication {

  public static final double HEIGHT = 500;
  public static final double WIDTH = 800;
  public static final double GAME_HEIGHT = 0.875 * HEIGHT;
  public static final double GAME_WIDTH = 0.75 * WIDTH;
  public static final String LAYOUTS_PATH = "layouts/";
  public static final String LEVEL_FILE = LAYOUTS_PATH + "level1.csv";

  private Stage myStage;
  private Scene myScene;
  private Layout myLayout;
  private BloonsCollection myBloons;
  private Map<Node, Node> blockToTower;
  private LayoutReader myLayoutReader;
  private Group myLevelLayout;
  private GameMenu myMenu;
  private VBox myMenuPane;
  private GameMenuInterface gameMenuController;
  private TowerMenuInterface towerMenuController;
  private AnimationHandler myAnimationHandler;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;
  private final ResourceBundle myBlockMappings = ResourceBundle
      .getBundle(getClass().getPackageName() + ".resources.blockMappings");

  public BloonsApplication(GameMenuInterface gameController, Layout layout, BloonsCollection bloons) {
    gameMenuController = gameController;
    myLayout = layout;
    myBloons = bloons;
  }

  public void fireInTheHole(Stage mainStage) {
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
    myAnimationHandler = new AnimationHandler(myLayout, myLevelLayout, myBloons,
        myStartingX, myStartingY, myBlockSize);
    towerMenuController = new TowerMenuController(GAME_WIDTH, GAME_HEIGHT, myBlockSize, myLevelLayout, myAnimationHandler);
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

  private void displaySettings(){
    FlowPane flow = new FlowPane();
    VBox settings = new VBox(new Label("EDDIE"));
    settings.setAlignment(Pos.CENTER);
    flow.getChildren().add(settings);
    myMenuPane.getChildren().add(flow);
  }

  private void visualizePlayerGUI(BorderPane level) {
    myMenuPane = new VBox();
    myMenuPane.setSpacing(10); //magic num
    myMenu = new GameMenu(myMenuPane, gameMenuController, towerMenuController);
    level.setRight(myMenuPane);
  }

  /**
   * This class makes a new alert message when there is an error.
   * @param header
   * @param message
   */
  public void makeAlert(String header, String message) {
    Alert a = new Alert(Alert.AlertType.NONE);
    ButtonType close = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
    a.getButtonTypes().addAll(close);
    a.setHeaderText(header);
    a.setContentText(message);
    a.show();
  }

  public void fullScreen(){
    myStage.setFullScreen(true);
    myStage.show();
  }

  public AnimationHandler getMyAnimationHandler() {
    return myAnimationHandler;
  }

}
