package ooga.visualization;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

public class StartWindow implements Window{

  Scene myScene;
  Button myStartButton;
  Button myResetButton;
  ResourceBundle myMenuButtonNames;
  ResourceBundle myApplicationMessages;
  String myCurrentLanguage;
  String myCurrentStylesheet;
  HBox myButtonGroup;

  public StartWindow(Scene scene, ResourceBundle menuButtonNames, ResourceBundle applicationMessages, String currentLanguage, String currentStylesheet){
    myScene = scene;
    myMenuButtonNames = menuButtonNames;
    myApplicationMessages = applicationMessages;
    myCurrentLanguage = currentLanguage;
    myCurrentStylesheet = currentStylesheet;
  }

  @Override
  public void displayWindow(List<Button> startButton) {
    BorderPane menu = new BorderPane();
    menu.getStyleClass().add("start-menu");
    myButtonGroup = new HBox();
    myButtonGroup.setAlignment(Pos.CENTER);

    myStartButton = startButton.get(0);
    myResetButton = startButton.get(1);

    setupWindowButtons();

    BorderPane.setAlignment(myButtonGroup, Pos.CENTER);
    menu.setBottom(myButtonGroup);
    myScene.setRoot(menu);
  }

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
    Path styles = null;
    try {
      styles = Paths.get(getClass().getClassLoader().getResource(BloonsApplication.STYLESHEETS).toURI());
    } catch (URISyntaxException e) {
      new AlertHandler(myApplicationMessages.getString("NoStyles"),
          myApplicationMessages.getString("NoStyles"));
    }
    for (File style : styles.toFile().listFiles()) {
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

  public ResourceBundle getMenuButtonNames(){
    return myMenuButtonNames;
  }

  public ResourceBundle getApplicationMessages(){
    return myApplicationMessages;
  }

  public String getCurrentLanguage() {
    return myCurrentLanguage;
  }

  public String getMyCurrentStylesheet() {
    return myCurrentStylesheet;
  }

}
