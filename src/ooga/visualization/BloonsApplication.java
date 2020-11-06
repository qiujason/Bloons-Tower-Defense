package ooga.visualization;

import java.net.URISyntaxException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import ooga.backend.LayoutReader;
import ooga.controller.MenuInterface;

public class BloonsApplication extends Application {

  public static final double HEIGHT = 500;
  public static final double WIDTH = 800;
  public static final double GAME_HEIGHT = 0.875 * HEIGHT;
  public static final double GAME_WIDTH = 0.75 * WIDTH;
  public static final String LAYOUTS_PATH = "layouts/";
  public static final String LEVEL_FILE = LAYOUTS_PATH + "example_level1.csv";
  public static final String TOWER_IMAGE = "/gamePhotos/monkey.jpg";
//  public static final double BUTTON_VBOX_HEIGHT = HEIGHT;
//  public static final double BUTTON_VBOX_WIDTH = WIDTH - GAME_WIDTH;
//  public static final double STATS_HBOX_HEIGHT = HEIGHT - GAME_HEIGHT;
//  public static final double STATS_HBOX_WIDTH = GAME_WIDTH;

  private Stage myStage;
  private Scene myScene;
  private LayoutReader myLayoutReader;
  private Group myLevelLayout;
  private GameMenu myMenu;
  private MenuInterface menuController;
  private AnimationHandler myAnimationHandler;
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
  }

  private void loadLevel() {
    BorderPane level = new BorderPane();
    myLayoutReader = new LayoutReader();
    visualizeGameScreen(level);
    myAnimationHandler = new AnimationHandler();
    level.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
    myScene = new Scene(level, WIDTH, HEIGHT);
    myStage.setScene(myScene);
  }

  private void visualizeGameScreen(BorderPane level) {
    visualizeLayout(level);
    visualizePlayerGUI(level);
  }

  private void visualizeLayout(BorderPane level) {
    myLevelLayout = new Group();
    level.setLeft(myLevelLayout);

    List<List<String>> layout = myLayoutReader.getLayoutFromFile(LEVEL_FILE);

    int numberOfRows = layout.size();
    int numberOfColumns = layout.get(0).size();
    double blockWidth = GAME_WIDTH / numberOfColumns;
    double blockHeight = GAME_HEIGHT / numberOfRows;
    double blockSize = Math.min(blockWidth, blockHeight);

    double currentBlockX = 0;
    double currentBlockY = 0;

    for (List<String> row : layout) {
      for (String block : row) {
        Rectangle newBlock = createBlock(block, currentBlockX, currentBlockY, blockSize);
        myLevelLayout.getChildren().add(newBlock);
        currentBlockX += blockSize;
      }
      currentBlockX = 0;
      currentBlockY += blockSize;
    }
  }

  private Rectangle createBlock(String block, double currentBlockX, double currentBlockY,
      double blockSize) {
    Rectangle blockRectangle = new Rectangle(currentBlockX, currentBlockY, blockSize, blockSize);
    String blockColorAsString = myBlockMappings.getString(block);
    Color blockColor = Color.valueOf(blockColorAsString);
    blockRectangle.setFill(blockColor);
    blockRectangle.setOnMouseClicked(e -> putTower(blockRectangle));
    blockRectangle.setId("LayoutBlock" + (int) currentBlockX
        + (int) currentBlockY); // Should find better way to setId
    return blockRectangle;
  }

  // FIXME: handle exception
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
  }

  private void visualizePlayerGUI(BorderPane level) {
    VBox menuPane = new VBox();
    menuPane.setSpacing(10); //magic num
    myMenu = new GameMenu(menuPane, menuController);
    level.setRight(menuPane);
  }
}
