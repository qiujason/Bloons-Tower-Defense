package ooga.visualization;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import ooga.controller.MenuInterface;
import javafx.scene.control.Button;

public class GameMenu {

  private static final String LANGUAGE = "English";
  private final ResourceBundle menuProperties = ResourceBundle
      .getBundle(getClass().getPackageName() + ".resources.menu" + LANGUAGE);

  private BorderPane myMenuPane;

  private Button playButton;
  private Button pauseButton;
  private Button speedButton;

  private static final String PLAY_TEXT = "PlayButton";
  private static final String PAUSE_TEXT = "PauseButton";
  private static final String SPEEDUP_TEXT = "SpeedUpButton";
  private static final String SLOWDOWN_TEXT = "SlowDownButton";

  public GameMenu(BorderPane MenuPane, MenuInterface controller) {
    myMenuPane = MenuPane;

    playButton = makeButton(menuProperties.getString(PLAY_TEXT), event -> controller.play());
    pauseButton = makeButton(menuProperties.getString(PAUSE_TEXT), event -> controller.pause());
    speedButton = makeButton(menuProperties.getString(SPEEDUP_TEXT), event -> controller.speedUp());
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
    myMenuPane.getChildren().add(button);
    return button;
  }
}