package ooga.visualization.menu;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import ooga.backend.towers.TowerType;
import ooga.controller.TowerMenuInterface;

public class WeaponButtonsMenu extends FlowPane {

  private TowerMenuInterface controller;
  private List<TowerType> weaponTypeList = Arrays.asList(TowerType.values());

  //later make this read in what package from a overall game properties file
  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";
  private static final String BUTTON_TAG = "Button";
  private static final String TOWER_COST_DIRECTORY = "towervalues/TowerBuyValues";

  private static final Double BUTTON_MAX_WIDTH = 50.0;

  private ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);
  private ResourceBundle towerCost = ResourceBundle.getBundle(TOWER_COST_DIRECTORY);

  public WeaponButtonsMenu(TowerMenuInterface controller){
    this.controller = controller;
    makeAllWeaponButtons();
    this.setPrefWrapLength(200);
    this.setOrientation(Orientation.HORIZONTAL);
  }

  private void makeAllWeaponButtons(){
    for(TowerType type : weaponTypeList){
      this.getChildren().add(makeWeaponButton(type,
          event -> newclass.makeTOwer.buyTower(type)));
    }
  }

  private Button makeWeaponButton(TowerType type, EventHandler<ActionEvent> handler){
    String towerName = typeToName.getString(type.name());
    String imageDirectory = nameToPicture.getString(towerName + BUTTON_TAG);
    Image towerImage = makeImage(imageDirectory);
    ImageView imageView = new ImageView(towerImage);
    imageView.setFitWidth(25);
    imageView.setFitHeight(25);
    Button button = new Button("",imageView);
    button.setOnAction(handler);
    button.setId(towerName);
    button.setMinHeight(25);
    button.setMinWidth(50);
    return button;
  }

  private Image makeImage(String directory){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(directory).toURI()));
    } catch (URISyntaxException e) {
      e.printStackTrace();  //duvall no like
    }
    assert towerImage != null;
    return towerImage;
  }

}
