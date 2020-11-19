package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.AlertHandler;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.controller.WeaponNodeHandler;
import ooga.visualization.menu.WeaponMenu;

public class TowerNode extends GamePieceNode{

  private final TowerType towerType;
  private final WeaponRange rangeDisplay;
  private final ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private final ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  private WeaponMenu towerMenu;

  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";

  public TowerNode(TowerType towerType, double xPosition, double yPosition, double radius){
    super(xPosition, yPosition, radius);
    this.towerType = towerType;
    this.setFill(findImage());
    this.setId(towerType.name());
    rangeDisplay = new WeaponRange(xPosition, yPosition, towerType.getRadius());

  }

  @Override
  public void setXPosition(double xPos){
    super.setXPosition(xPos);
    rangeDisplay.setCenterX(xPos);
  }

  @Override
  public void setYPosition(double yPos){
    super.setYPosition(yPos);
    rangeDisplay.setCenterY(yPos);
  }

  @Override
  public ImagePattern findImage(){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(nameToPicture.getString(typeToName.getString(towerType.name()))).toURI()));
    } catch (
        URISyntaxException e) {
      new AlertHandler("Image Not Found", towerType.name() + " image not found.");
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }

  public void setWeaponRange(double gridRadius, double blockSize){
    rangeDisplay.setRadius(gridRadius * blockSize);
  }

  public void makeTowerMenu(WeaponNodeHandler weaponNodeHandler, String language){
    towerMenu = new WeaponMenu(this, weaponNodeHandler, language);
  }

  public WeaponMenu getTowerMenu(){
    return towerMenu;
  }

  public WeaponRange getRangeDisplay(){
    return rangeDisplay;
  }

  public TowerType getTowerType(){
    return towerType;
  }

  public void hideRangeDisplay(){
    rangeDisplay.makeInvisible();
  }

  public void showRangeDisplay(){
    rangeDisplay.makeVisible();
  }



}
