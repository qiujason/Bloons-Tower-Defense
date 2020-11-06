package ooga.visualization;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.controller.GameMenuInterface;
import javafx.scene.control.Button;

public class GameMenu {

  private static final String LANGUAGE = "English";
  private final ResourceBundle menuProperties = ResourceBundle
      .getBundle(getClass().getPackageName() + ".resources.menu" + LANGUAGE);

  private VBox myMenuPane;

  private Button playButton;
  private Button pauseButton;
  private Button speedUpButton;
  private Button slowDownButton;
  private Button dartTowerButton;
  private Button tackTowerButton;

  private static final String PLAY_TEXT = "PlayButton";
  private static final String PAUSE_TEXT = "PauseButton";
  private static final String SPEEDUP_TEXT = "SpeedUpButton";
  private static final String SLOWDOWN_TEXT = "SlowDownButton";

  private static final String DART_TOWER_NAME = "DartTower";
  private static final String DART_TOWER_COST = "$100";
  private static final String DART_TOWER_IMAGE = "/gamePhotos/dart_monkey_icon.png";
  private static final String TACK_TOWER_IMAGE = "/gamePhotos/";

  private static Double BUTTON_WIDTH = 100.0;

  public GameMenu(VBox MenuPane, GameMenuInterface controller) {
    myMenuPane = MenuPane;
    playButton = makeButton(menuProperties.getString(PLAY_TEXT), event -> controller.play());
    pauseButton = makeButton(menuProperties.getString(PAUSE_TEXT), event -> controller.pause());
    makeButtonRow(playButton, pauseButton);

    speedUpButton = makeButton(menuProperties.getString(SPEEDUP_TEXT), event -> controller.speedUp());
    slowDownButton = makeButton(menuProperties.getString(SLOWDOWN_TEXT), event -> controller.slowDown());
    makeButtonRow(speedUpButton, slowDownButton);

    dartTowerButton = makeTowerButton(DART_TOWER_NAME, DART_TOWER_IMAGE, event -> controller.play());
    makeTowerRow(DART_TOWER_NAME, DART_TOWER_COST, dartTowerButton);
  }

  private void makeButtonRow(Button first, Button second){
    HBox buttonRow = new HBox();
    buttonRow.getChildren().addAll(first, second);
    myMenuPane.getChildren().add(buttonRow);
  }

  private void makeTowerRow(String name, String cost, Button towerButton){
    HBox towerRow = new HBox();
    Label towerLabel = new Label(name + ": " + cost);
    towerRow.getChildren().addAll(towerLabel, towerButton);
    myMenuPane.getChildren().add(towerRow);
  }

  /**
   * From lecture
   * @author Robert Duvall
   */
  private Button makeButton(String name, EventHandler<ActionEvent> handler) {
    Button button = new Button();
    button.setText(name);
    button.setOnAction(handler);
    button.setId(name);
    button.setMinWidth(BUTTON_WIDTH);
    return button;
  }

  private Button makeTowerButton(String towerName, String imageDirectory, EventHandler<ActionEvent> handler){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(imageDirectory).toURI()));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    ImageView imageView = new ImageView(towerImage);
    imageView.setFitHeight(90);
    imageView.setFitWidth(90);
    Button button = new Button("",imageView);
    button.setOnAction(handler);
    button.setId(towerName);
    return button;
  }

}