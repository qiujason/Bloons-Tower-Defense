package ooga.visualization.menu;

import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import ooga.backend.towers.ShootingChoice;

public class ShootingChoiceBox extends ComboBox {

  public ShootingChoiceBox(String name, double buttonWidth) {
    List<ShootingChoice> shootingChoices = Arrays.asList(ShootingChoice.values());
    this.setPromptText(name);
    this.setItems(FXCollections.observableList(shootingChoices));
    this.setId(name);
    this.setMaxWidth(buttonWidth);
    this.setMinWidth(buttonWidth);
  }

}
