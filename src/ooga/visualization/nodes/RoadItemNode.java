package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.AlertHandler;
import ooga.backend.roaditems.RoadItemType;

/**
 * This class is the front end node for Road Items.
 */
public class RoadItemNode extends GamePieceNode {

  private final RoadItemType roadItemType;
  private final ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private final ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";
  private static final String IMAGE_ERROR = "Image Not Found";
  private static final String NOT_FOUND = " image not found";

  /**
   * Constructor which creates the front end RoadItemNode and fills it with the appropriate image
   * @param roadItemType the type of RoadItem being made
   * @param xPosition the x position
   * @param yPosition the y position
   * @param radius the radius of the node
   */
  public RoadItemNode(RoadItemType roadItemType, double xPosition, double yPosition,
      double radius) {
    super(xPosition, yPosition, radius);
    this.roadItemType = roadItemType;
    this.setFill(findImage());
    this.setId(roadItemType.name());
  }

  /**
   * Finds the appropriate image for the RoadItemNode based on its type.
   * @return ImagePattern that is used to fill the node
   */
  @Override
  public ImagePattern findImage() {
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(
          getClass().getResource(nameToPicture.getString(typeToName.getString(roadItemType.name())))
              .toURI()));
    } catch (
        URISyntaxException e) {
      new AlertHandler(IMAGE_ERROR, roadItemType.name() + NOT_FOUND);
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }
}