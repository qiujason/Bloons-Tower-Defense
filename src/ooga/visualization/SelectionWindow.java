package ooga.visualization;

import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SelectionWindow implements Window {

//  public SelectionWindow

  @Override
  public void displayWindow(List<Button> windowChangeButtons) {
//    BorderPane levelSelectScreen = new BorderPane();
//    levelSelectScreen.getStyleClass().add("level-background");
//    Text levelSelectText = new Text("Select Level");
//    levelSelectText.getStyleClass().add("menu-text");
//    levelSelectText.setScaleX(Double.parseDouble(BloonsApplication.MENU_RESOURCES.getString("SelectLevelPromptSize")));
//    levelSelectText.setScaleY(Double.parseDouble(BloonsApplication.MENU_RESOURCES.getString("SelectLevelPromptSize")));
//    levelSelectScreen.setCenter(levelSelectText);
//
//    BorderPane.setAlignment(levelSelectText, Pos.CENTER);
//
//    ComboBox<Enum<?>> gameModeOptions = initializeGameModeOptions();
//
//    ComboBox<String> levelOptions = initializeLevelButtons(levelSelectScreen);
//
//    Button backButton = new Button(myMenuButtonNames.getString("ReturnToStart"));
//    backButton.setOnAction(e -> startApplication(myStage));
//    backButton.setId("BackButton");
//
//    Button startLevelButton = new Button(myMenuButtonNames.getString("StartGame"));
//    startLevelButton.setOnAction(e -> loadLevel(levelOptions.getValue() + ".csv"));
//    startLevelButton.setId("StartLevelButton");
//
//    HBox levelSelectButtons = new HBox();
//    levelSelectButtons.setAlignment(Pos.CENTER);
//    levelSelectButtons.getChildren().add(levelOptions);
//    levelSelectButtons.getChildren().add(gameModeOptions);
//    levelSelectButtons.getChildren().add(startLevelButton);
//    levelSelectButtons.getChildren().add(backButton);
//
//    levelSelectScreen.setBottom(levelSelectButtons);
//
//    myScene.setRoot(levelSelectScreen);
  }

  @Override
  public void setupWindowButtons() {

  }

}
