package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.AlertHandler;
import ooga.backend.roaditems.RoadItemType;

public class RoadItemNode extends GamePieceNode {

  private final RoadItemType roadItemType;
  private final ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private final ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";

  public RoadItemNode(RoadItemType roadItemType, double xPosition, double yPosition, double radius) {
    super(xPosition, yPosition, radius);
    this.roadItemType = roadItemType;
    this.setFill(findImage());
    this.setId(roadItemType.name());
  }

  @Override
  public ImagePattern findImage(){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(nameToPicture.getString(typeToName.getString(roadItemType.name()))).toURI()));
    } catch (
        URISyntaxException e) {
      new AlertHandler("Image Not Found", roadItemType.name() + " image not found.");
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }
}