package ooga.visualization.menu;

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
import ooga.controller.TowerMenuInterface;
import ooga.visualization.AnimationHandler;
import ooga.visualization.BloonsApplication;

public class GameMenu {

  private static final String LANGUAGE = "English";
  private final ResourceBundle menuProperties = ResourceBundle
      .getBundle(getClass().getPackageName() + ".resources.menu" + LANGUAGE);

  private VBox myMenuPane;
  private AnimationHandler myAnimationHandler;

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
  private static final String DART_TOWER_IMAGE = "/gamePhotos/dartmonkeybutton.png";
  private static final String TOWER_IMAGE = "/gamePhotos/dartmonkey.png";

  private static final String TACK_TOWER_IMAGE = "/gamePhotos/";

  private static Double BUTTON_WIDTH = 100.0;

  public GameMenu(BloonsApplication App, VBox MenuPane, GameMenuInterface gameController, TowerMenuInterface towerController,
      AnimationHandler animationHandler) {
    myMenuPane = MenuPane;
    myAnimationHandler = animationHandler;

    playButton = makeButton(menuProperties.getString(PLAY_TEXT), event -> gameController.play());
    pauseButton = makeButton(menuProperties.getString(PAUSE_TEXT), event -> gameController.pause());
    makeButtonRow(playButton, pauseButton);

    speedUpButton = makeButton(menuProperties.getString(SPEEDUP_TEXT), event -> gameController.speedUp());
    slowDownButton = makeButton(menuProperties.getString(SLOWDOWN_TEXT), event -> gameController.slowDown());
    makeButtonRow(speedUpButton, slowDownButton);

    dartTowerButton = makeTowerButton(DART_TOWER_NAME, DART_TOWER_IMAGE, event -> App.createTower());
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
    Image towerImage = makeImage(imageDirectory);
    ImageView imageView = new ImageView(towerImage);
    imageView.setFitHeight(90);
    imageView.setFitWidth(90);
    Button button = new Button("",imageView);
    button.setOnAction(handler);
    button.setId(towerName);
    return button;
  }

  private Image makeImage(String directory){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(directory).toURI()));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    assert towerImage != null;
    return towerImage;
  }



}