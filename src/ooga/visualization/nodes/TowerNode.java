package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ooga.backend.towers.TowerType;

public class TowerNode extends GamePieceNode{

  private TowerType towerType;

  private WeaponRange rangeDisplay;
  private Boolean rangeBoolean;

  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";

  private ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  public TowerNode(TowerType towerType, double xPosition, double yPosition, double radius){
    super(xPosition, yPosition, radius);
    this.towerType = towerType;
    this.setFill(findImage());
    rangeDisplay = new WeaponRange(xPosition, yPosition, towerType.getRadius());
    rangeBoolean = true;
  }

  public WeaponRange getRangeDisplay(){
    return rangeDisplay;
  }

  public TowerType getTowerType(){
    return towerType;
  }

  public Boolean rangeShown(){
    return rangeBoolean;
  }

  public void hideRangeDisplay(){
    rangeDisplay.makeInvisible();
    rangeBoolean = false;
  }

  public void showRangeDisplay(){
    rangeDisplay.makeVisible();
    rangeBoolean = true;
  }

  @Override
  public ImagePattern findImage(){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(nameToPicture.getString(typeToName.getString(towerType.name()))).toURI()));
    } catch (
        URISyntaxException e) {
      e.printStackTrace();
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }

}
