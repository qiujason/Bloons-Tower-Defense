package ooga.visualization.menu;

import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import ooga.backend.towers.ShootingChoice;

public class ShootingChoiceBox extends ComboBox {

  private static final String SHOOTING_CHOICE = "Shooting Choice";

  private List<ShootingChoice> shootingChoices = Arrays.asList(ShootingChoice.values());

  public ShootingChoiceBox(double buttonWidth){
    this.setPromptText(SHOOTING_CHOICE);
    this.setItems(FXCollections.observableList(shootingChoices));
    this.setMaxWidth(buttonWidth);
    this.setMinWidth(buttonWidth);
  }

}
