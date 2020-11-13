package ooga.visualization.weapons;

import java.net.URISyntaxException;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ooga.backend.towers.TowerType;

public class TowerNode extends Circle {

  private TowerType towerType;
  private double xPosition;
  private double yPosition;
  private double radius;
  private WeaponRange rangeDisplay;

  private static final String TOWER_IMAGE = "/gamePhotos/dartmonkey.png";

  public TowerNode(TowerType towerType, double xPosition, double yPosition, double radius){
    super(xPosition, yPosition, radius);
    this.towerType = towerType;
    this.setFill(findTowerImage());
    rangeDisplay = new WeaponRange(xPosition, yPosition, towerType.getRadius());
  }

  public WeaponRange getRangeDisplay(){
    return rangeDisplay;
  }

  public void hideRangeDisplay(){
    rangeDisplay.makeInvisible();
  }

  public void showRangeDisplay(){
    rangeDisplay.makeVisible();
  }

  private ImagePattern findTowerImage(){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(TOWER_IMAGE).toURI()));
    } catch (
        URISyntaxException e) {
      e.printStackTrace();
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }

}
