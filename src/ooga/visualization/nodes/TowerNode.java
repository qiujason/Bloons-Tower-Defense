package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.backend.towers.TowerType;
import ooga.controller.TowerMenuInterface;
import ooga.visualization.menu.WeaponMenu;

public class TowerNode extends GamePieceNode{

  private TowerType towerType;
  private WeaponRange rangeDisplay;
  private Boolean rangeBoolean;
  private WeaponMenu towerMenu;

  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";

  private ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  public TowerNode(TowerType towerType, double xPosition, double yPosition, double radius){
    super(xPosition, yPosition, radius);
    this.towerType = towerType;
    this.setFill(findImage(typeToName.getString(towerType.name())));
    rangeDisplay = new WeaponRange(xPosition, yPosition, towerType.getRadius());
    rangeBoolean = true;
  }

  public void makeTowerMenu(TowerMenuInterface controller){
    towerMenu = new WeaponMenu(controller);
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
    return null;
  }

  @Override
  public ImagePattern findImage(String towerName){
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
