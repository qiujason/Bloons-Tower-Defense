package ooga.visualization;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ooga.controller.MenuInterface;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class GameMenu {

  private VBox menuPane;
  private MenuInterface menuController;

  private Button playButton;
  private Button pauseButton;
  private Button speedButton;

  private static final String PLAY_TEXT = "Play";
  private static final String PAUSE_TEXT = "Pause";
  private static final String SPEEDUP_TEXT = "SpeedUp";
  private static final String SLOWDOWN_TEXT = "SlowDown";

  public GameMenu(VBox myMenuPane, MenuInterface myController) {
    menuPane = myMenuPane;
    menuController = myController;

    playButton = makeButton(PLAY_TEXT, event -> menuController.play());
    pauseButton = makeButton(PAUSE_TEXT, event -> menuController.pause());
    speedButton = makeButton(SPEEDUP_TEXT, event -> menuController.speedUp());

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
    menuPane.getChildren().add(button);
    return button;
  }
}
