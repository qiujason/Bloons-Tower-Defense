package ooga.visualization.menu;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import ooga.backend.towers.ShootingChoice;
import ooga.controller.WeaponNodeInterface;
import ooga.visualization.nodes.TowerNode;

/**
 * This class holds the menu for individual towers for upgrades, shooting choices, and selling
 */
public class WeaponMenu extends FlowPane {

  private static final String RESOURCE_DIRECTORY = "ooga.visualization.resources.languages.";
  private static final String MENU = ".menu";
  private static final String MULTI_TAG = "Multi";
  private static final String UPGRADE_RANGE = "RangeUpgradeButton";
  private static final String UPGRADE_RATE = "RateUpgradeButton";
  private static final String SELL_TOWER = "SellTowerButton";
  private static final String SHOOTING_CHOICE = "ShootingChoiceBox";
  private static final Double BUTTON_WIDTH = 100.0;

  private ResourceBundle menuProperties;
  private ShootingChoiceBox shootingChoiceBox = null;

  /**
   * Constructor that creates the WeaponMenu for the TowerNode it should be connected to. Makes
   * all of the buttons and connects the functionality from the WeaponNodeInterface.
   *
   * @param tower the selected TowerNode that the WeaponMenu should be connected to
   * @param weaponNodeHandler the WeaponNodeInterface holding the functionality the WeaponMenu buttons
   *                          should perform
   * @param language the language that the game is set in
   */
  public WeaponMenu(TowerNode tower, WeaponNodeInterface weaponNodeHandler, String language) {
    initializeResourceBundle(language);
    this.setPrefWrapLength(200);
    makeButton(menuProperties.getString(UPGRADE_RANGE), event -> weaponNodeHandler
        .upgradeRange(tower));
    makeButton(menuProperties.getString(UPGRADE_RATE), event -> weaponNodeHandler
        .upgradeRate(tower));
    makeButton(menuProperties.getString(SELL_TOWER), event -> weaponNodeHandler
        .removeWeapon(tower));
    if (!isSpreadTower(tower)) {
      shootingChoiceBox = new ShootingChoiceBox(menuProperties.getString(SHOOTING_CHOICE),
          BUTTON_WIDTH);
      setComboBoxHandler(event -> weaponNodeHandler
          .setTargetingOption(tower, (ShootingChoice) shootingChoiceBox.getValue()));
    }
  }

  private void makeButton(String name, EventHandler<ActionEvent> handler) {
    Button button = new Button();
    button.setText(name);
    button.setOnAction(handler);
    button.setId(name);
    button.setMinWidth(BUTTON_WIDTH);
    button.setMaxWidth(BUTTON_WIDTH);
    this.getChildren().add(button);
  }

  private void setComboBoxHandler(EventHandler<ActionEvent> handler) {
    shootingChoiceBox.setOnAction(handler);
    this.getChildren().add(shootingChoiceBox);
  }

  private void initializeResourceBundle(String language) {
    menuProperties = ResourceBundle.getBundle(RESOURCE_DIRECTORY + language + MENU + language);
  }

  private boolean isSpreadTower(TowerNode towerNode) {
    return (towerNode.getTowerType().name().contains(MULTI_TAG));
  }
}
