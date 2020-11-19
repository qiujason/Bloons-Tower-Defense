package ooga.visualization.menu;

import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.backend.roaditems.RoadItemType;

public class RoadItemDescription extends VBox {

  private static final String ROAD_ITEM_COST_DIRECTORY = "towervalues/roadItemBuyValues";

  private ResourceBundle itemCost = ResourceBundle.getBundle(ROAD_ITEM_COST_DIRECTORY);

  public RoadItemDescription(RoadItemType itemType, String language){
    this.getChildren().add(new Label(itemType.name()));
    this.getChildren().add(new Label(itemCost.getString(itemType.name())));
  }

}
