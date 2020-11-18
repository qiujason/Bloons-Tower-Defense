package ooga.visualization.menu;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import ooga.backend.towers.ShootingChoice;
import ooga.controller.TowerNodeHandler;
import ooga.visualization.nodes.TowerNode;

public class WeaponMenu extends FlowPane {

  private static final String LANGUAGE = "English";
  private final ResourceBundle menuProperties =
      ResourceBundle.getBundle("ooga.visualization.resources.languages." + LANGUAGE + ".menu" + LANGUAGE);

  private static final String UPGRADE_RANGE_TEXT = "RangeUpgradeButton";
  private static final String UPGRADE_RATE_TEXT = "RateUpgradeButton";
  private static final String SELL_TOWER_TEXT = "SellTowerButton";
  private static final String CLOSE_MENU_TEXT = "CloseMenuButton";
  private static final Double BUTTON_WIDTH = 100.0;

  private Button upgradeRangeButton;
  private Button upgradeRateButton;
  private Button sellTowerButton;
  private Button closeMenuButton;
  private ComboBox shootingChoiceBox;

  public WeaponMenu(TowerNode tower, TowerNodeHandler towerNodeHandler){
    this.setPrefWrapLength(200);
    upgradeRangeButton = makeButton(menuProperties.getString(UPGRADE_RANGE_TEXT), event -> towerNodeHandler.upgradeRange(tower));
    upgradeRateButton = makeButton(menuProperties.getString(UPGRADE_RATE_TEXT), event -> towerNodeHandler.upgradeRate(tower));
    sellTowerButton = makeButton(menuProperties.getString(SELL_TOWER_TEXT), event -> towerNodeHandler.removeWeapon(tower));
    shootingChoiceBox = new ShootingChoiceBox(BUTTON_WIDTH);
    setComboBoxHandler(event -> towerNodeHandler.setTargetingOption(tower, (ShootingChoice) shootingChoiceBox.getValue()));
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

  private void setComboBoxHandler(EventHandler<ActionEvent> handler){
    shootingChoiceBox.setOnAction(handler);
    this.getChildren().add(shootingChoiceBox);
  }

}
