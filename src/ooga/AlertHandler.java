package ooga;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class AlertHandler {

  /**
   * This class makes a new alert message when there is an error.
   * @param header
   * @param message
   */
  public AlertHandler(String header, String message){
    Alert a = new Alert(Alert.AlertType.NONE);
    ButtonType close = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
    a.getButtonTypes().addAll(close);
    a.setHeaderText(header);
    a.setContentText(message);
    a.show();
  }
  /**
   * This class makes a new alert message when there is an error.
   * @param header
   * @param message
   */
  public void makeAlert(String header, String message) {

  }

}
