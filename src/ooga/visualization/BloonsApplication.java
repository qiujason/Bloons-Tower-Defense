package ooga.visualization;

import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.controller.MenuInterface;

public class BloonsApplication extends Application {

  public static final double HEIGHT = 500;
  public static final double WIDTH = 800;
  public static final double GAME_HEIGHT = 0.875 * HEIGHT;
  public static final double GAME_WIDTH = 0.75 * WIDTH;
  public static final double BUTTON_VBOX_HEIGHT = HEIGHT;
  public static final double BUTTON_VBOX_WIDTH = WIDTH - GAME_WIDTH;
  public static final double STATS_HBOX_HEIGHT = HEIGHT - GAME_HEIGHT;
  public static final double STATS_HBOX_WIDTH = GAME_WIDTH;

  private Stage myStage;
  private Scene myScene;
  private GameMenu myMenu;
  private MenuInterface menuController;
  private AnimationHandler myAnimationHandler;
  private final ResourceBundle blockMappings = ResourceBundle
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
    Group levelExample = new Group();
    visualizeGameScreen(levelExample);
    myAnimationHandler = new AnimationHandler();
    myScene = new Scene(levelExample, WIDTH, HEIGHT);
    myScene.setFill(Color.LIGHTGRAY);
    myStage.setScene(myScene);
  }

  private void visualizeGameScreen(Group level) {
    //visualizeLayout(level);
    visualizePlayerGUI(level);
  }

  // FIXME: Mostly pseudocode for now
//  private void visualizeLayout(Group level) {
//    Layout layout = getLayoutOrSomething();
//    double currentBlockX = 0;
//    double currentBlockY = 0;
//    int numberOfRows = layout.size();
//    int numberOfColumns = layout.get(0).size();
//    double blockWidth = GAME_WIDTH / numberOfColumns;
//    double blockHeight = GAME_HEIGHT / numberOfRows;
//
//    for (List<LayoutBlock> row : layout) {
//      for (LayoutBlock block : row) {
//        Rectangle newBlock = createBlock(block, currentBlockX, currentBlockY, blockWidth,
//            blockHeight);
//        level.getChildren().add(newBlock);
//        currentBlockX += blockWidth;
//      }
//      currentBlockX = 0;
//      currentBlockY += blockHeight;
//    }
//  }
//
//  // FIXME: Pseudocode here too
//  private Rectangle createBlock(LayoutBlock block, double currentBlockX, double currentBlockY,
//      double blockWidth, double blockHeight) {
//    String blockColorAsString = blockMappings.getString(block.getValueOrSomething());
//    Color blockColor = Color.valueOf(blockColorAsString);
//    return new Rectangle(currentBlockX, currentBlockY, blockWidth, blockHeight);
//  }

  private void visualizePlayerGUI(Group level) {
    // TODO: Fill in with appropriate buttons
    VBox menuPane = new VBox();
    menuPane.setSpacing(10); //magic num
    myMenu = new GameMenu(menuPane, menuController);
    level.getChildren().add(menuPane);
  }
}
