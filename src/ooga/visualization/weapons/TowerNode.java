package ooga.visualization.weapons;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ooga.backend.towers.TowerType;

public class TowerNode extends Circle {

  private TowerType towerType;
  private WeaponRange rangeDisplay;

  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";

  private ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  public TowerNode(TowerType towerType, double xPosition, double yPosition, double radius){
    super(xPosition, yPosition, radius);
    this.towerType = towerType;
    this.setFill(findTowerImage(typeToName.getString(towerType.name())));
    rangeDisplay = new WeaponRange(xPosition, yPosition, towerType.getRadius());
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

  private ImagePattern findTowerImage(String towerName){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(nameToPicture.getString(towerName)).toURI()));
    } catch (
        URISyntaxException e) {
      e.printStackTrace();
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }

}
