package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.AlertHandler;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.Specials;

/**
 * This class is the node class for the Bloons on the screen. It extends the GamePieceNode abstract class.
 */

public class BloonNode extends GamePieceNode {

  private BloonsType bloonType;
  public static String BLOON_IMAGES_PATH = "bloon_resources/BloonImages";

  private final ResourceBundle myBloonImages = ResourceBundle
      .getBundle(BLOON_IMAGES_PATH);

  /**
   * Creates an instance of the BloonNode class.
   * @param bloonType the bloon type that this node will represent
   * @param xPosition the x-coordinate of the node
   * @param yPosition the y-coordinate of the node
   * @param radius the radius of the node
   */
  public BloonNode(BloonsType bloonType, double xPosition, double yPosition, double radius) {
    super(xPosition, yPosition, radius);
    this.bloonType = bloonType;
    this.setFill(findImage());
  }

  /**
   * Finds the correct image of the Bloon based on its bloon type. This method gets the image from the
   * properties file that maps a Bloon type to its image
   * @return
   */
  @Override
  public ImagePattern findImage() {
    Image towerImage = null;
    try {
      StringBuilder imageName = new StringBuilder(bloonType.name());
      if (bloonType.specials() != Specials.None) {
        imageName.append("_").append(bloonType.specials().name().toUpperCase());
      }
      towerImage = new Image(String.valueOf(getClass().getResource(myBloonImages.getString(
          imageName.toString())).toURI()));
    } catch (
        URISyntaxException e) {
      new AlertHandler("Image Not Found", bloonType.name() + " image not found.");
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }

  /**
   * Updates the bloon type based on the given bloon type and finds the correct image.
   * @param bloonsType
   */
  public void updateBloonType(BloonsType bloonsType) {
    if (bloonsType != bloonType) {
      bloonType = bloonsType;
      if (!bloonType.name().equals("DEAD")) {
        setImage();
      }
    }
  }

  /**
   * Helper method to set the image of the node.
   */
  private void setImage() {
    this.setFill(findImage());
  }

}
