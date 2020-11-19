package ooga.visualization.menu;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.TowerType;
import ooga.controller.WeaponNodeHandler;
import ooga.controller.WeaponNodeInterface;

public class WeaponButtonsMenu extends FlowPane {

  private WeaponNodeInterface weaponNodeHandler;
  private List<TowerType> weaponTypeList = Arrays.asList(TowerType.values());
  private List<RoadItemType> roadItemTypeList = Arrays.asList(RoadItemType.values());
  private Map<Button, VBox> buttonWeaponDescription;

  //later make this read in what package from a overall game properties file
  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";
  private static final String BUTTON_TAG = "Button";
  private static final String ROAD_ITEMS = "RoadItems";

  private static final Double BUTTON_HEIGHT = 25.0;
  private static final Double BUTTON_WIDTH = 50.0;
  private static final Double PREF_WRAP_LENGTH = 200.0;

  private String currentLanguage;

  private ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);
  private ResourceBundle roadItemPic = ResourceBundle.getBundle(PACKAGE + ROAD_ITEMS);


  public WeaponButtonsMenu(WeaponNodeInterface weaponNodeHandler, String language){
    this.weaponNodeHandler = weaponNodeHandler;
    currentLanguage = language;

    buttonWeaponDescription = new HashMap<>();

    makeAllWeaponButtons();
    makeAllRoadItemButtons();
    this.setPrefWrapLength(PREF_WRAP_LENGTH);
    this.setOrientation(Orientation.HORIZONTAL);
  }

  private void makeAllWeaponButtons(){
    for(TowerType type : weaponTypeList){
      Button weaponButton = makeWeaponButton(type, event -> weaponNodeHandler.makeWeapon(type));
      weaponButton.setId(type.toString() + "Button");
      System.out.println(weaponButton.getId());
      buttonWeaponDescription.put(weaponButton, new TowerDescription(type, currentLanguage));
      showButtonDescription(weaponButton);
      this.getChildren().add(weaponButton);
    }
  }

  private void makeAllRoadItemButtons(){
    for(RoadItemType type : roadItemTypeList){
      Button itemButton = makeRoadItemButton(type, event -> weaponNodeHandler.makeRoadWeapon(type));
      itemButton.setId(type.toString() + "Button");
      buttonWeaponDescription.put(itemButton, new RoadItemDescription(type, currentLanguage));
      showButtonDescription(itemButton);
      this.getChildren().add(itemButton);
    }
  }

  private Button makeWeaponButton(TowerType type, EventHandler<ActionEvent> handler){
    String towerName = typeToName.getString(type.name());
    String imageDirectory = nameToPicture.getString(towerName + BUTTON_TAG);
    Button button = addImageToButton(imageDirectory);
    button.setOnAction(handler);
    button.setId(towerName);
    return button;
  }

  private Button makeRoadItemButton(RoadItemType type, EventHandler<ActionEvent> handler){
    String imageDirectory = roadItemPic.getString(type.name());
    Button button = addImageToButton(imageDirectory);
    button.setOnAction(handler);
    button.setId(type.name());
    return button;
  }

  private Button addImageToButton(String imageDirectory) {
    Image weaponImage = makeImage(imageDirectory);
    ImageView imageView = new ImageView(weaponImage);
    imageView.setFitWidth(BUTTON_HEIGHT);
    imageView.setFitHeight(BUTTON_HEIGHT);
    Button button = new Button("", imageView);
    button.setMinHeight(BUTTON_HEIGHT);
    button.setMinWidth(BUTTON_WIDTH);
    return button;
  }

  private Image makeImage(String directory){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(directory).toURI()));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    assert towerImage != null;
    return towerImage;
  }

  private void showButtonDescription(Button button){
    button.setOnMouseEntered(e -> {
      this.getChildren().add(buttonWeaponDescription.get(button));
      button.setOnMouseExited(event -> {
        this.getChildren().remove(buttonWeaponDescription.get(button));
      });
    });
  }
}