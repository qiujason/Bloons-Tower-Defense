package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.AlertHandler;
import ooga.backend.towers.TowerType;
import ooga.controller.WeaponNodeInterface;
import ooga.visualization.menu.WeaponMenu;

/**
 * This class is the front end TowerNode for Towers
 */
public class TowerNode extends GamePieceNode {

  private final TowerType towerType;
  private final WeaponRange rangeDisplay;
  private final ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private final ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  private WeaponMenu towerMenu;

  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";

  /**
   * Constructor which creates the front end TowerNode and fills it with the appropriate image as
   * well as creating the appropriate weapon range display based on the tower type
   *
   * @param towerType the TowerType of the TowerNode
   * @param xPosition the x position of the node
   * @param yPosition the y position of the node
   * @param radius the radius of the node
   */
  public TowerNode(TowerType towerType, double xPosition, double yPosition, double radius) {
    super(xPosition, yPosition, radius);
    this.towerType = towerType;
    this.setFill(findImage());
    this.setId(towerType.name());
    rangeDisplay = new WeaponRange(xPosition, yPosition, towerType.getRadius());

  }

  /**
   * @param xPos the new x position the TowerNode and its range display will be set to
   */
  @Override
  public void setXPosition(double xPos) {
    super.setXPosition(xPos);
    rangeDisplay.setCenterX(xPos);
  }

  /**
   * @param yPos the new y position the TowerNode and its range display will be set to
   */
  @Override
  public void setYPosition(double yPos) {
    super.setYPosition(yPos);
    rangeDisplay.setCenterY(yPos);
  }

  /**
   * Finds the appropriate image for the TowerNode based on its type.
   * @return ImagePattern that is used to fill the node
   */
  @Override
  public ImagePattern findImage() {
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(
          getClass().getResource(nameToPicture.getString(typeToName.getString(towerType.name())))
              .toURI()));
    } catch (
        URISyntaxException e) {
      new AlertHandler("Image Not Found", towerType.name() + " image not found.");
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }

  /**
   * Sets the new range display by converting the grid radius into the frontend screen size radius
   * @param gridRadius the range radius in terms of blocks in a grid of blocks
   * @param blockSize the size of the block in the frontend
   */
  public void setWeaponRange(double gridRadius, double blockSize) {
    rangeDisplay.setRadius(gridRadius * blockSize);
  }

  /**
   * Creates the tower menu for this specific TowerNode. Takes in the WeaponNodeInterface as it sets
   * the functionalities for this menu.
   *
   * @param weaponNodeHandler WeaponNodeInterface holding the functionality for the TowerMenu buttons
   * @param language the language the game is set in
   */
  public void makeTowerMenu(WeaponNodeInterface weaponNodeHandler, String language) {
    towerMenu = new WeaponMenu(this, weaponNodeHandler, language);
  }

  /**
   * @return this TowerNode's specific WeaponMenu
   */
  public WeaponMenu getTowerMenu() {
    return towerMenu;
  }

  /**
   * @return this TowerNode's specific range display
   */
  public WeaponRange getRangeDisplay() {
    return rangeDisplay;
  }

  /**
   * @return the TowerType of this TowerNode
   */
  public TowerType getTowerType() {
    return towerType;
  }

  /**
   * Hides the weapon range display
   */
  public void hideRangeDisplay() {
    rangeDisplay.makeInvisible();
  }

  /**
   * Shows the weapon range display
   */
  public void showRangeDisplay() {
    rangeDisplay.makeVisible();
  }

}