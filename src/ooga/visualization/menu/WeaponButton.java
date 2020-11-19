package ooga.visualization.menu;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.TowerType;
import ooga.controller.WeaponNodeInterface;

public class WeaponButton extends Button implements Describable {

  private final WeaponNodeInterface weaponNodeHandler;

  private String currentLanguage;
  private WeaponDescription weaponDescription;
  private static final double BUTTON_HEIGHT = 25.0;
  private static final double BUTTON_WIDTH = 50.0;

  public WeaponButton(String text, ImageView imageView, String weaponType,
      WeaponNodeInterface weaponNodeHandler, String language){
    super(text, imageView);
    this.weaponNodeHandler = weaponNodeHandler;
    currentLanguage = language;
    setWeaponDescription(weaponType);
    setButtonHandler(weaponType);
    this.setMinHeight(BUTTON_HEIGHT);
    this.setMinWidth(BUTTON_WIDTH);
  }

  @Override
  public void setWeaponDescription(String weaponType) {
    weaponDescription = new WeaponDescription(weaponType, currentLanguage);
  }

  @Override
  public WeaponDescription getWeaponDescription(){
    return weaponDescription;
  }

  private void setButtonHandler(String weaponType){
    if(isTowerType(weaponType)){
      System.out.println(TowerType.fromString(weaponType).name());
      this.setOnAction(event -> weaponNodeHandler.makeWeapon(TowerType.fromString(weaponType)));
    }
    else if(isRoadItemType(weaponType)){
      System.out.println(RoadItemType.fromString(weaponType));
      this.setOnAction(event -> weaponNodeHandler.makeRoadWeapon(RoadItemType.fromString(weaponType)));
    }
  }

  private boolean isTowerType(String weaponType){
    return TowerType.isEnumName(weaponType);
  }

  private boolean isRoadItemType(String weaponType){
    return RoadItemType.isEnumName(weaponType);
  }

}
