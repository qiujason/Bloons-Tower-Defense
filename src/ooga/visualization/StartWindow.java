package ooga.visualization;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ooga.AlertHandler;
import ooga.controller.Controller;

/**
 * This class implements a StartWindow, which is first window seen when the application is started.
 * This window includes buttons to set language, style, start a game in a new window, or proceed to
 * the level select screen (SelectionWindow)
 */
public class StartWindow implements Window {

  Scene myScene;
  Button myStartButton;
  Button myResetButton;
  ResourceBundle myMenuButtonNames;
  ResourceBundle myApplicationMessages;
  String myCurrentLanguage;
  String myCurrentStylesheet;
  HBox myButtonGroup;

  /**
   * Constructor for a StartWindow. Initializes the Scene, ResourcesBundles, and current
   * language/style necessary to properly visualize the Window.
   *
   * @param scene               the Scene object of the calling application
   * @param menuButtonNames     ResourceBundle with the current names for menu buttons
   * @param applicationMessages ResourceBundle including error messages for the application
   * @param currentLanguage     the current language of the game
   * @param currentStylesheet   the current style for the game
   */
  public StartWindow(Scene scene, ResourceBundle menuButtonNames,
      ResourceBundle applicationMessages, String currentLanguage, String currentStylesheet) {
    myScene = scene;
    myMenuButtonNames = menuButtonNames;
    myApplicationMessages = applicationMessages;
    myCurrentLanguage = currentLanguage;
    myCurrentStylesheet = currentStylesheet;
  }

  /**
   * @see Window#displayWindow(List)
   */
  @Override
  public void displayWindow(List<Button> windowChangeButtons) {
    myStartButton = windowChangeButtons.get(0);
    myResetButton = windowChangeButtons.get(1);

    BorderPane menu = new BorderPane();
    menu.getStyleClass().add("start-menu");
    myButtonGroup = new HBox();
    myButtonGroup.setAlignment(Pos.CENTER);

    setupWindowButtons();

    BorderPane.setAlignment(myButtonGroup, Pos.CENTER);
    menu.setBottom(myButtonGroup);
    myScene.setRoot(menu);
  }

  /**
   * @see Window#setupWindowButtons()
   */
  @Override
  public void setupWindowButtons() {
    myStartButton.setText(myMenuButtonNames.getString("Start"));
    myStartButton.setId("Start");
    myButtonGroup.getChildren().add(myStartButton);

    setupLanguageOptions(myButtonGroup);

    Button newGameWindowButton = new Button(myMenuButtonNames.getString("NewGameWindow"));
    newGameWindowButton.setId("NewGameWindowButton");
    newGameWindowButton.setOnAction(e -> {
      Controller newWindow = new Controller();
      newWindow.start(new Stage());
    });
    myButtonGroup.getChildren().add(newGameWindowButton);

    setupStyleOptions(myButtonGroup);
  }

  private void setupLanguageOptions(HBox buttonGroup) {
    ComboBox<String> languageOptions = new ComboBox<>();
    languageOptions.setPromptText(myMenuButtonNames.getString("SetLanguage"));
    for (String language : BloonsApplication.LANGUAGES.keySet()) {
      languageOptions.getItems().add(language);
    }
    languageOptions.setId("LanguageOptions");
    languageOptions.setOnAction(e -> changeLanguage(languageOptions.getValue()));
    buttonGroup.getChildren().add(languageOptions);
  }

  private void changeLanguage(String language) {
    myCurrentLanguage = language;
    myMenuButtonNames = ResourceBundle
        .getBundle(
            getClass().getPackageName() + ".resources.languages." + myCurrentLanguage
                + ".startMenuButtonNames"
                + myCurrentLanguage);
    myApplicationMessages = ResourceBundle
        .getBundle(
            getClass().getPackageName() + ".resources.languages." + myCurrentLanguage
                + ".applicationMessages"
                + myCurrentLanguage);
    myResetButton.fire();
  }

  private void setupStyleOptions(HBox buttonGroup) {
    ComboBox<String> styleOptions = new ComboBox<>();
    styleOptions.setId("StyleOptions");
    styleOptions.setPromptText(myMenuButtonNames.getString("SetStyle"));
    Path styles = getStyles();
    setupStyleHBox(buttonGroup, styleOptions, styles);
  }

  private Path getStyles() {
    Path styles = null;
    try {
      styles = Paths.get(Objects
          .requireNonNull(getClass().getClassLoader().getResource(BloonsApplication.STYLESHEETS))
          .toURI());
    } catch (URISyntaxException e) {
      new AlertHandler(myApplicationMessages.getString("NoStyles"),
          myApplicationMessages.getString("NoStyles"));
    }
    return styles;
  }

  private void setupStyleHBox(HBox buttonGroup, ComboBox<String> styleOptions, Path styles) {
    for (File style : Objects.requireNonNull(styles.toFile().listFiles())) {
      if (style.getName().contains(".")) {
        styleOptions.getItems().add(style.getName().split("\\.")[0]);
      }
    }
    styleOptions.setOnAction(e -> {
          myCurrentStylesheet = styleOptions.getValue() + ".css";
          myResetButton.fire();
        }
    );
    buttonGroup.getChildren().add(styleOptions);
  }

  /**
   * Gets the current menu button names for the Window
   *
   * @return ResourceBundle for the current menu button names
   */
  public ResourceBundle getMenuButtonNames() {
    return myMenuButtonNames;
  }

  /**
   * Gets the current application error messages for the Window
   *
   * @return ResourceBundle for the current application errors
   */
  public ResourceBundle getApplicationMessages() {
    return myApplicationMessages;
  }

  /**
   * Gets the current language of the Window
   *
   * @return the current language
   */
  public String getCurrentLanguage() {
    return myCurrentLanguage;
  }

  /**
   * Gets the current style of the Window
   *
   * @return the current style
   */
  public String getMyCurrentStylesheet() {
    return myCurrentStylesheet;
  }

}
