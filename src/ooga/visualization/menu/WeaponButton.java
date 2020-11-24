package ooga.visualization.menu;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * This class extends Button and is meant to be an overarching Button object for both Towers and
 * Road Items. It implements the Describable interface to create and get its button descriptions.
 */
public class WeaponButton extends Button implements Describable {

  private final String currentLanguage;
  private WeaponDescription weaponDescription;
  private static final double BUTTON_HEIGHT = 25.0;
  private static final double BUTTON_WIDTH = 50.0;

  /**
   * Constructor for WeaponButton.
   *
   * @param text the text of the Button, but is an empty String because the buttons don't need text
   * @param imageView the ImageView object of the picture the button should show
   * @param weaponType the WeaponType of the button that affects what description is made
   * @param language the language that the game is set in to be able to change the weapon description
   *                 language
   */
  public WeaponButton(String text, ImageView imageView, String weaponType,
      String language) {
    super(text, imageView);
    currentLanguage = language;
    setWeaponDescription(weaponType);
    this.setMinHeight(BUTTON_HEIGHT);
    this.setMinWidth(BUTTON_WIDTH);
  }

  /**
   * Creates a WeaponDescription for this button.
   *
   * @param weaponType holds the WeaponType(String value of TowerType or RoadItemType)
   */
  @Override
  public void setWeaponDescription(String weaponType) {
    weaponDescription = new WeaponDescription(weaponType, currentLanguage);
  }

  /**
   * @return the button's WeaponDescription
   */
  @Override
  public WeaponDescription getWeaponDescription() {
    return weaponDescription;
  }

}
