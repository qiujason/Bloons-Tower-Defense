package ooga.visualization.menu;

import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ooga.backend.towers.TowerType;

public class WeaponDescription extends VBox {

  private static final String TOWER_COSTS = "towervalues/TowerBuyValues";
  private static final String ITEM_COSTS = "towervalues/roadItemBuyValues";
  private static final String DIRECTORY = "ooga.visualization.resources.languages.";
  private static final String DESCRIPTION = ".weaponDescription";
  private static final String DESCRIBE_TAG = "Description";
  private static final String COST_TAG = "$";
  private static final double WRAP_WIDTH = 200.0;
  private static final Font NAME_FONT = Font.font(20);
  private static final Font DESCRIPTOR_FONT = Font.font(15);

  private final ResourceBundle towerCosts = ResourceBundle.getBundle(TOWER_COSTS);
  private final ResourceBundle itemCosts = ResourceBundle.getBundle(ITEM_COSTS);
  private ResourceBundle descriptionResource;

  public WeaponDescription(String weaponType, String language) {
    initializeResourceBundle(language);
    nameText(weaponType);
    costText(weaponType);
    descriptionText(weaponType);
  }

  private void nameText(String weaponType){
    Text name = new Text(descriptionResource.getString(weaponType));
    name.setFont(NAME_FONT);
    this.getChildren().add(name);
  }

  private void costText(String weaponType){
    Text cost;
    if(TowerType.isEnumName(weaponType)){
      cost = new Text(COST_TAG + towerCosts.getString(weaponType));
    }
    else {
      cost = new Text(COST_TAG + itemCosts.getString(weaponType));
    }
    cost.setFont(DESCRIPTOR_FONT);
    this.getChildren().add(cost);
  }

  private void descriptionText(String weaponType){
    Text description = new Text(descriptionResource.getString(weaponType + DESCRIBE_TAG));
    description.setFont(DESCRIPTOR_FONT);
    description.setWrappingWidth(WRAP_WIDTH);
    this.getChildren().add(description);
  }

  private void initializeResourceBundle(String language){
    descriptionResource = ResourceBundle.getBundle(DIRECTORY + language + DESCRIPTION + language);
  }
}
