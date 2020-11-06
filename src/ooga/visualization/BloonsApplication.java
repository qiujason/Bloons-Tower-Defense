package ooga.visualization;

import java.net.URISyntaxException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.backend.readers.LayoutReader;
import ooga.controller.GameMenuController;
import ooga.controller.GameMenuInterface;

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
  private List<List<String>> myLayout;
  private LayoutReader myLayoutReader;
  private Group myLevelLayout;
  private GameMenu myMenu;
  private GameMenuInterface gameMenuController;
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
    menu.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
  }

  private void loadLevel() {
    BorderPane level = new BorderPane();
    myLayoutReader = new LayoutReader();
//    visualizeGameScreen(level);
    visualizeLayout(level);
    myAnimationHandler = new AnimationHandler(myLayout, myLevelLayout, myStartingX, myStartingY, myBlockSize);
    gameMenuController = new GameMenuController(myAnimationHandler.getAnimation());
    visualizePlayerGUI(level);
    level.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
    myScene = new Scene(level, WIDTH, HEIGHT);
    myStage.setScene(myScene);
  }

//  private void visualizeGameScreen(BorderPane level) {
//    visualizeLayout(level);
//    visualizePlayerGUI(level);
//  }

  // TODO: Refactor
  private void visualizeLayout(BorderPane level) {
    myLevelLayout = new Group();
    level.setLeft(myLevelLayout);

    myLayout = myLayoutReader.getDataFromFile(LEVEL_FILE);

    int numberOfRows = myLayout.size();
    int numberOfColumns = myLayout.get(0).size();
    double blockWidth = GAME_WIDTH / numberOfColumns;
    double blockHeight = GAME_HEIGHT / numberOfRows;
    myBlockSize = Math.min(blockWidth, blockHeight);

    double currentBlockX = 0;
    double currentBlockY = 0;
    int blockNumberX = 0;
    int blockNumberY = 0;

    for (List<String> row : myLayout) {
      for (String block : row) {
        Rectangle newBlock = createBlock(block, currentBlockX, currentBlockY, myBlockSize);
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
    Color blockColor = Color.valueOf(blockColorAsString);
    blockRectangle.setFill(blockColor);
    blockRectangle.setOnMouseClicked(e -> putTower(blockRectangle));
    if(block.charAt(0) == '*') {
      myStartingX = currentBlockX + blockSize / 2;
      myStartingY = currentBlockY + blockSize / 2;
    }
    return blockRectangle;
  }

  // TODO: handle exception/refactor
  private void putTower(Rectangle blockRectangle) {
    Color playableBlock = Color.valueOf(myBlockMappings.getString("0"));
    Color nonPlayableBlock = Color.valueOf(myBlockMappings.getString(">"));
    if (blockRectangle.getFill().equals(playableBlock)) {
      Image towerImage = null;
      try {
        towerImage = new Image(String.valueOf(getClass().getResource(TOWER_IMAGE).toURI()));
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
      assert towerImage != null;
      ImagePattern towerImagePattern = new ImagePattern(towerImage);
      blockRectangle.setFill(towerImagePattern);
    } else if (!blockRectangle.getFill().equals(nonPlayableBlock)) {
      blockRectangle.setFill(playableBlock);
    }
    else {
      myAnimationHandler.getAnimation().pause();
      makeAlert("Invalid Tower Space", "You cannot place a tower there :(");
    }
  }

  private void visualizePlayerGUI(BorderPane level) {
    VBox menuPane = new VBox();
    menuPane.setSpacing(10); //magic num
    myMenu = new GameMenu(menuPane, gameMenuController);
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
