package ooga.visualization.menu;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class WeaponButton extends Button implements Describable {

  private final String currentLanguage;
  private WeaponDescription weaponDescription;
  private static final double BUTTON_HEIGHT = 25.0;
  private static final double BUTTON_WIDTH = 50.0;

  public WeaponButton(String text, ImageView imageView, String weaponType,
      String language) {
    super(text, imageView);
    currentLanguage = language;
    setWeaponDescription(weaponType);
    this.setMinHeight(BUTTON_HEIGHT);
    this.setMinWidth(BUTTON_WIDTH);
  }

  @Override
  public void setWeaponDescription(String weaponType) {
    weaponDescription = new WeaponDescription(weaponType, currentLanguage);
  }

  @Override
  public WeaponDescription getWeaponDescription() {
    return weaponDescription;
  }

}
