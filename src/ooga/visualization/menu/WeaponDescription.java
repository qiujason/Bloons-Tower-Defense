package ooga.visualization.menu;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WeaponDescription extends VBox {

  public WeaponDescription(String weaponType, String language) {
    this.getChildren().add(new Label(weaponType));
  }

}
