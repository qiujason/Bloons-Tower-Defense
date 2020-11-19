package ooga.visualization.menu;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Orientation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import ooga.AlertHandler;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.TowerType;
import ooga.controller.WeaponNodeInterface;

public class WeaponButtonsMenu extends FlowPane {

  private final WeaponNodeInterface weaponNodeHandler;
  private final List<TowerType> weaponTypeList = Arrays.asList(TowerType.values());
  private final List<RoadItemType> roadItemTypeList = Arrays.asList(RoadItemType.values());

  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";
  private static final String BUTTON_TAG = "Button";
  private static final String ALERT_PACKAGE = "ooga.visualization.resources.languages.";
  private static final String APP_MESSAGE = ".applicationMessages";
  private static final String NO_IMAGE_HEADER = "NoImageHeader";
  private static final String NO_BUTTON_IMAGE = "NoButtonImage";

  private static final double IMAGE_SIZE = 25.0;
  private static final Double PREF_WRAP_LENGTH = 200.0;

  private final String currentLanguage;
  private final ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private final ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  private ResourceBundle alertMessages;
  private List<String> allTypeList;


  public WeaponButtonsMenu(WeaponNodeInterface weaponNodeHandler, String language){
    this.weaponNodeHandler = weaponNodeHandler;
    currentLanguage = language;
    combineTypeLists();
    initializeAlertProperties(language);
    makeAllWeaponButtons();
    this.setPrefWrapLength(PREF_WRAP_LENGTH);
    this.setOrientation(Orientation.HORIZONTAL);
  }

  private void makeAllWeaponButtons(){
    for(String type : allTypeList){
      WeaponButton weaponButton = makeWeaponButton(type);
      showButtonDescription(weaponButton);
      this.getChildren().add(weaponButton);
    }
  }

  private WeaponButton makeWeaponButton(String weaponType){
    String weaponName = typeToName.getString(weaponType);
    String imageDirectory = nameToPicture.getString(weaponName + BUTTON_TAG);
    WeaponButton button = addImageToButton(imageDirectory, weaponType);
    setButtonHandler(button, weaponType);
    button.setId(weaponName + BUTTON_TAG);
    return button;
  }

  private WeaponButton addImageToButton(String imageDirectory, String weaponType) {
    Image weaponImage = makeImage(imageDirectory);
    ImageView imageView = new ImageView(weaponImage);
    imageView.setFitWidth(IMAGE_SIZE);
    imageView.setFitHeight(IMAGE_SIZE);
    return new WeaponButton("", imageView, weaponType, weaponNodeHandler, currentLanguage);
  }

  private Image makeImage(String directory){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(directory).toURI()));
    } catch (URISyntaxException e) {
      new AlertHandler(alertMessages.getString(NO_IMAGE_HEADER), alertMessages.getString(NO_BUTTON_IMAGE));
    }
    assert towerImage != null;
    return towerImage;
  }

  private void showButtonDescription(WeaponButton button){
    button.setOnMouseEntered(e -> {
      this.getChildren().add(button.getWeaponDescription());
      button.setOnMouseExited(
          event -> this.getChildren().remove(button.getWeaponDescription()));
    });
  }



  private void setButtonHandler(WeaponButton button, String weaponType){
    if(isTowerType(weaponType)){
      button.setOnAction(event -> weaponNodeHandler.makeWeapon(TowerType.fromString(weaponType)));
    }
    else if(isRoadItemType(weaponType)){
      button.setOnAction(event -> weaponNodeHandler.makeRoadWeapon(RoadItemType.fromString(weaponType)));
    }
  }

  private boolean isTowerType(String weaponType){
    return TowerType.isEnumName(weaponType);
  }

  private boolean isRoadItemType(String weaponType){
    return RoadItemType.isEnumName(weaponType);
  }

  private void combineTypeLists(){
    allTypeList = new ArrayList<>();
    for(TowerType towerType : weaponTypeList){
      allTypeList.add(towerType.name());
    }
    for(RoadItemType itemType : roadItemTypeList){
      allTypeList.add(itemType.name());
    }
  }

  private void initializeAlertProperties(String language){
    alertMessages = ResourceBundle.getBundle(ALERT_PACKAGE + language + APP_MESSAGE + language);
  }
}