package ooga.visualization.menu;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import ooga.controller.GameMenuInterface;

/**
 * The GameMenu holding all of the core in-game buttons.
 */
public class GameMenu extends FlowPane {

  private final List<GameButtonType> gameButtonList = Arrays.asList(GameButtonType.values());
  private final Map<GameButtonType, EventHandler<ActionEvent>> buttonHandleMap;

  private ResourceBundle menuProperties;

  private static final String RESOURCE_DIRECTORY = "ooga.visualization.resources.languages.";
  private static final String MENU = ".menu";
  private static final Double BUTTON_WIDTH = 100.0;
  private static final Double PREF_WRAP_LENGTH = 200.0;

  /**
   * Constructor for the GameMenu that initializes the GameMenuInterface which holds all of the
   * methods for the buttons' functionalities and the language that the current game is set to.
   *
   * @param menuController the GameMenuInterface holding the functionality of the buttons
   * @param language the language that the game is set to
   */
  public GameMenu(GameMenuInterface menuController, String language) {
    initializeResourceBundle(language);
    buttonHandleMap = menuController.getButtonHandleMap();
    makeAllButtons();
    this.setPrefWrapLength(PREF_WRAP_LENGTH);
    this.setOrientation(Orientation.HORIZONTAL);
  }

  private void initializeResourceBundle(String language) {
    menuProperties = ResourceBundle.getBundle(RESOURCE_DIRECTORY + language + MENU + language);
  }

  private void makeAllButtons() {
    for (GameButtonType type : gameButtonList) {
      this.getChildren().add(makeButton(type));
    }
  }

  /**
   * From lecture
   *
   * @author Robert Duvall
   */
  private Button makeButton(GameButtonType buttonType) {
    Button button = new Button();
    button.setText(menuProperties.getString(buttonType.name()));
    button.setOnAction(buttonHandleMap.get(buttonType));
    button.setId(buttonType.name());
    if (buttonType.toString().equals("QuitButton")) {
      button.setMinWidth(2 * BUTTON_WIDTH);
      button.setMaxWidth(2 * BUTTON_WIDTH);
    } else {
      button.setMinWidth(BUTTON_WIDTH);
      button.setMaxWidth(BUTTON_WIDTH);
    }
    return button;
  }
}