package ooga.visualization;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

  private static final String PLAY_TEXT = "PlayButton";
  private static final String PAUSE_TEXT = "PauseButton";
  private static final String SPEEDUP_TEXT = "SpeedUpButton";
  private static final String SLOWDOWN_TEXT = "SlowDownButton";

  private static Double BUTTON_WIDTH = 120.0;

  public GameMenu(VBox MenuPane, GameMenuInterface controller) {
    myMenuPane = MenuPane;

    playButton = makeButton(menuProperties.getString(PLAY_TEXT), event -> controller.play());
    pauseButton = makeButton(menuProperties.getString(PAUSE_TEXT), event -> controller.pause());
    makeButtonRow(playButton, pauseButton);

    speedUpButton = makeButton(menuProperties.getString(SPEEDUP_TEXT), event -> controller.speedUp());
    slowDownButton = makeButton(menuProperties.getString(SLOWDOWN_TEXT), event -> controller.slowDown());
    makeButtonRow(speedUpButton, slowDownButton);
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
    button.setMaxWidth(BUTTON_WIDTH);
    return button;
  }
}