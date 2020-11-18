package ooga.visualization.menu;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.controller.GameMenuInterface;
import javafx.scene.control.Button;
import ooga.controller.TowerMenuInterface;
import ooga.controller.TowerNodeHandler;

public class GameMenu {

  private static final String LANGUAGE = "English";
  private final ResourceBundle menuProperties =
      ResourceBundle.getBundle("ooga.visualization.resources.languages." + LANGUAGE + ".menu" + LANGUAGE);

  private static final String PLAY_TEXT = "PlayButton";
  private static final String PAUSE_TEXT = "PauseButton";
  private static final String SPEEDUP_TEXT = "SpeedUpButton";
  private static final String SLOWDOWN_TEXT = "SlowDownButton";
  private static final Double BUTTON_WIDTH = 100.0;

  private VBox myMenuPane;

  private Button playButton;
  private Button pauseButton;
  private Button speedUpButton;
  private Button slowDownButton;

  public GameMenu(VBox MenuPane, GameMenuInterface gameController,
      TowerMenuInterface towerController, TowerNodeHandler towerNodeController) {
    myMenuPane = MenuPane;

    playButton = makeButton(menuProperties.getString(PLAY_TEXT), event -> gameController.play());
    pauseButton = makeButton(menuProperties.getString(PAUSE_TEXT), event -> gameController.pause());
    makeButtonRow(playButton, pauseButton);

    speedUpButton = makeButton(menuProperties.getString(SPEEDUP_TEXT), event -> gameController.speedUp());
    slowDownButton = makeButton(menuProperties.getString(SLOWDOWN_TEXT), event -> gameController.slowDown());
    makeButtonRow(speedUpButton, slowDownButton);

    myMenuPane.getChildren().add(new WeaponButtonsMenu(towerController, towerNodeController));
  }

  private void makeButtonRow(Button first, Button second){
    HBox buttonRow = new HBox();
    buttonRow.getChildren().addAll(first, second);
    myMenuPane.getChildren().add(buttonRow);
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
    button.setMaxWidth(BUTTON_WIDTH);
    return button;
  }
}