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
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.TowerType;
import ooga.controller.WeaponNodeHandler;

public class WeaponButtonsMenu extends FlowPane {

  private WeaponNodeHandler weaponNodeHandler;
  private List<TowerType> weaponTypeList = Arrays.asList(TowerType.values());
  private List<RoadItemType> roadItemTypeList = Arrays.asList(RoadItemType.values());

  //later make this read in what package from a overall game properties file
  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";
  private static final String BUTTON_TAG = "Button";
  private static final String ROAD_ITEMS = "RoadItems";
  private static final String TOWER_COST_DIRECTORY = "towervalues/TowerBuyValues";

  private static final Double BUTTON_MAX_WIDTH = 50.0;

  private ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);
  private ResourceBundle towerCost = ResourceBundle.getBundle(TOWER_COST_DIRECTORY);

  private ResourceBundle roadItemPic = ResourceBundle.getBundle(PACKAGE + ROAD_ITEMS);

  public WeaponButtonsMenu(WeaponNodeHandler weaponNodeHandler){
    this.weaponNodeHandler = weaponNodeHandler;
    makeAllWeaponButtons();
    makeAllRoadItemButtons();
    this.setPrefWrapLength(200);
    this.setOrientation(Orientation.HORIZONTAL);
  }

  private void makeAllWeaponButtons(){
    for(TowerType type : weaponTypeList){
      this.getChildren().add(makeWeaponButton(type,
          event -> weaponNodeHandler.makeWeapon(type)));
    }
  }

  private void makeAllRoadItemButtons(){
    for(RoadItemType type : roadItemTypeList){
      this.getChildren().add(makeRoadItemButton(type,
          event -> weaponNodeHandler.makeRoadWeapon(type)));
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

  private Button makeRoadItemButton(RoadItemType type, EventHandler<ActionEvent> handler){
    String imageDirectory = roadItemPic.getString(type.name());
    Image towerImage = makeImage(imageDirectory);
    ImageView imageView = new ImageView(towerImage);
    imageView.setFitWidth(25);
    imageView.setFitHeight(25);
    Button button = new Button("", imageView);
    button.setOnAction(handler);
    button.setId(type.name());
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
