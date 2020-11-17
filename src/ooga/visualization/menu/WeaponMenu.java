package ooga.visualization.menu;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import ooga.backend.towers.Tower;
import ooga.controller.TowerMenuInterface;
import ooga.visualization.nodes.TowerNode;

public class WeaponMenu extends FlowPane {

  private static final String LANGUAGE = "English";
  private final ResourceBundle menuProperties =
      ResourceBundle.getBundle("ooga.visualization.resources.menu" + LANGUAGE);

  private static final String UPGRADE_RANGE_TEXT = "RangeUpgradeButton";
  private static final String UPGRADE_RATE_TEXT = "RateUpgradeButton";
  private static final String SELL_TOWER_TEXT = "SellTowerButton";
  private static final String CLOSE_MENU_TEXT = "CloseMenuButton";
  private static final Double BUTTON_WIDTH = 100.0;

  private Button upgradeRangeButton;
  private Button upgradeRateButton;
  private Button sellTowerButton;
  private Button closeMenuButton;

  public WeaponMenu(Tower tower, TowerMenuInterface controller){
    this.setPrefWrapLength(200);
    upgradeRangeButton = makeButton(menuProperties.getString(UPGRADE_RANGE_TEXT), event -> controller.upgradeRange(tower));
    upgradeRateButton = makeButton(menuProperties.getString(UPGRADE_RATE_TEXT), event -> controller.upgradeRate(tower));
    sellTowerButton = makeButton(menuProperties.getString(SELL_TOWER_TEXT), event -> controller.sellTower(tower));
    //targetingOption ...
  }

  private Button makeButton(String name, EventHandler<ActionEvent> handler) {
    Button button = new Button();
    button.setText(name);
    button.setOnAction(handler);
    button.setId(name);
    button.setMinWidth(BUTTON_WIDTH);
    button.setMaxWidth(BUTTON_WIDTH);
    this.getChildren().add(button);
    return button;
  }

}
