package ooga.visualization.menu;


import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.backend.towers.TowerType;

public class TowerDescription extends VBox {

  private static final String TOWER_COST_DIRECTORY = "towervalues/TowerBuyValues";

  private ResourceBundle towerCost = ResourceBundle.getBundle(TOWER_COST_DIRECTORY);

  public TowerDescription(TowerType weaponType, String language){
    this.getChildren().add(new Label(weaponType.name()));
    this.getChildren().add(new Label(towerCost.getString(weaponType.name())));
  }

}
