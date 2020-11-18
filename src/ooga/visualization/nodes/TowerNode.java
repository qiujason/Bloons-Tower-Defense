package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.controller.TowerMenuInterface;
import ooga.visualization.menu.WeaponMenu;

public class TowerNode extends GamePieceNode{

  private Tower tower;
  private TowerType towerType;
  private WeaponRange rangeDisplay;
  private WeaponMenu towerMenu;

  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";

  private ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  public TowerNode(TowerType tower, double xPosition, double yPosition, double radius){
    super(xPosition, yPosition, radius);
    this.towerType = tower;
    this.setFill(findImage());
    rangeDisplay = new WeaponRange(xPosition, yPosition, tower.getRadius());
  }

  public void setWeaponRange(double blockSize){
    rangeDisplay.setRadius(tower.getRadius());
  }

  public void makeTowerMenu(Tower tower, TowerMenuInterface controller){
    towerMenu = new WeaponMenu(tower, controller);
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
