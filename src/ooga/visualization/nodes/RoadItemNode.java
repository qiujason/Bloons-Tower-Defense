package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.backend.roaditems.RoadItemType;

public class RoadItemNode extends GamePieceNode {

  private RoadItemType roadItemType;

  private static final String PACKAGE = "btd_towers/";
  private static final String ROAD_ITEMS = "RoadItems";

  private ResourceBundle roadItemPic = ResourceBundle.getBundle(PACKAGE + ROAD_ITEMS);

  public RoadItemNode(RoadItemType roadItemType, double xPosition, double yPosition, double radius) {
    super(xPosition, yPosition, radius);
    this.roadItemType = roadItemType;
    this.setFill(findImage());
  }


  @Override
  public ImagePattern findImage(){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(roadItemPic.getString(roadItemType.name())).toURI()));
    } catch (
        URISyntaxException e) {
      e.printStackTrace();
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }
}
