package ooga.visualization.menu;

import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import ooga.backend.towers.ShootingChoice;

/**
 * This class extends the ComboBox and its purpose is to be a drop down menu for a tower's different
 * shooting choices.
 */
public class ShootingChoiceBox extends ComboBox {

  /**
   * The ShootingChoiceBox constructor which makes a List of shooting choices based on the
   * ShootingChoice enum class and creates the ComboBox with those shooting choice options.
   *
   * @param name the name of the ComboBox
   * @param buttonWidth the width of the ComboBox
   */
  public ShootingChoiceBox(String name, double buttonWidth) {
    List<ShootingChoice> shootingChoices = Arrays.asList(ShootingChoice.values());
    this.setPromptText(name);
    this.setItems(FXCollections.observableList(shootingChoices));
    this.setId(name);
    this.setMaxWidth(buttonWidth);
    this.setMinWidth(buttonWidth);
  }

}
