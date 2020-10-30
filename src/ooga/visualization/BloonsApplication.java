package ooga.visualization;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BloonsApplication extends Application {

  public static final double HEIGHT = 500;
  public static final double WIDTH = 800;

  Stage myStage;
  Scene myScene;

  @Override
  public void start(Stage mainStage) throws Exception {
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
    BorderPane.setAlignment(startButton, Pos.CENTER);
    menu.setBottom(startButton);
  }

  private void loadLevel() {
    Group levelExample = new Group();
    Rectangle gameSpace = new Rectangle(0, 0,3 * WIDTH / 4, 7 * HEIGHT / 8);
    gameSpace.setFill(Color.GREENYELLOW);
    levelExample.getChildren().add(gameSpace);
    myScene = new Scene(levelExample, WIDTH, HEIGHT);
    myScene.setFill(Color.LIGHTGRAY);
    myStage.setScene(myScene);
  }
}
